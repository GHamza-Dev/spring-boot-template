package com.warya.base.payment;

import com.warya.base.payment.dto.PaymentLinkRequest;
import com.warya.base.payment.dto.PaymentLinkResponse;
import com.warya.base.payment.dto.PaymentStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class PaymentService {
    private final RestTemplate restTemplate;

    @Value("${mxplus.api.url}")
    private String apiUrl;

    @Value("${mxplus.merchant.id}")
    private String merchantId;

    @Value("${mxplus.merchant.name}")
    private String merchantName;

    @Value("${mxplus.website.id}")
    private String websiteId;

    @Value("${mxplus.website.name}")
    private String websiteName;

    @Value("${mxplus.institution.id}")
    private String institutionId;

    @Value("${mxplus.user}")
    private String user;

    @Value("${mxplus.password}")
    private String password;

    @Value("${mxplus.key.public}")
    private String publicKey;

    @Value("${app.base.url}")
    private String appBaseUrl;

    public PaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PaymentLinkResponse createPaymentLink(PaymentLinkRequest request) {
        // 1. Get secure token first
        String token = getSecureToken();

        // 2. Format amount - MXPlus expects amounts without decimal points
        BigDecimal amount = BigDecimal.valueOf(request.getAmount())
                .setScale(2, RoundingMode.HALF_UP);
        String formattedAmount = amount.multiply(BigDecimal.valueOf(100))
                .setScale(0)
                .toString();

        // 3. Create request payload
        Map<String, String> payload = new HashMap<>();

        // Transaction info
        payload.put("capture", "Y");  // Automatic capture
        payload.put("transactiontype", "0");  // Regular payment, not pre-authorization
        payload.put("currency", request.getCurrency());
        payload.put("orderid", request.getOrderReference());
        payload.put("recurring", "N");  // Not a recurring payment
        payload.put("amount", formattedAmount);
        payload.put("securtoken24", token);
        // Create MAC value per documentation
        payload.put("mac_value", DigestUtils.md5DigestAsHex((request.getOrderReference() + formattedAmount).getBytes(StandardCharsets.UTF_8)));

        // Merchant info
        payload.put("merchantid", merchantId);
        payload.put("merchantname", merchantName);
        payload.put("websitename", websiteName);
        payload.put("websiteid", websiteId);
        payload.put("successURL", appBaseUrl + "/payment/success");
        payload.put("failURL", appBaseUrl + "/payment/fail");
//        payload.put("failURL", "http://localhost:8080/api/warya/fail");
        payload.put("callbackurl", appBaseUrl + "/api/payments/callback");

        // Client info
        payload.put("fname", request.getFirstName());
        payload.put("lname", request.getLastName());
        payload.put("email", request.getEmail());
        payload.put("phone", request.getPhone());
        payload.put("address", request.getAddress());
        payload.put("city", request.getCity());
        payload.put("zipcode", request.getZipCode());
        payload.put("country", request.getCountry());
        payload.put("id_client", "");  // Empty for non-card-on-file payments
        payload.put("token", "");  // Empty for new payments

        // 4. Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 5. Make the API request
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<PaymentLinkResponse> response = restTemplate.exchange(
                    apiUrl + "/linkpayment",
                    HttpMethod.POST,
                    entity,
                    PaymentLinkResponse.class
            );

            return response.getBody();
        } catch (Exception e) {
            log.error("Error creating payment link: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create payment link", e);
        }
    }

    public PaymentStatus checkPaymentStatus(String orderid) {
        // 1. Get secure token first
        String token = getSecureToken();

        // 2. Create request payload
        Map<String, String> payload = new HashMap<>();
        payload.put("orderid", orderid);
        payload.put("authnumber", "");  // Can be empty when checking by orderid
        payload.put("amount", "");  // Can be empty when checking by orderid
        payload.put("securtoken24", token);
        payload.put("mac_value", DigestUtils.md5DigestAsHex(orderid.getBytes(StandardCharsets.UTF_8)));
        payload.put("merchantid", merchantId);

        // 3. Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 4. Make the API request
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<PaymentStatus> response = restTemplate.exchange(
                    apiUrl + "/status",
                    HttpMethod.POST,
                    entity,
                    PaymentStatus.class
            );

            return response.getBody();
        } catch (Exception e) {
            log.error("Error checking payment status: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to check payment status", e);
        }
    }

    public boolean processPaymentCallback(Map<String, String> callbackData) {
        // Extract data from callback
        String repauto = callbackData.get("repauto");
        String amount = callbackData.get("montant");
        String signature = callbackData.get("signature");
        String numAuto = callbackData.get("numAuto");
        String numTrans = callbackData.get("numTrans");
        String idcommande = callbackData.get("idcommande");
        String carte = callbackData.get("carte");
        String typecarte = callbackData.get("typecarte");
        String token = callbackData.get("token");

        // Validate signature
        String expectedSignature = DigestUtils.md5DigestAsHex((idcommande + repauto + publicKey + amount).getBytes(StandardCharsets.UTF_8));

        if (!expectedSignature.equals(signature)) {
            log.error("Invalid signature in payment callback");
            return false;
        }

        // Check if transaction was successful (repauto = "00" means success)
        if ("00".equals(repauto)) {
            // TODO: Update your database with the successful payment
            // Store transaction details in your system
            log.info("Successful payment received for order: {}", idcommande);
            return true;
        } else {
            log.warn("Failed payment received for order: {}, code: {}", idcommande, repauto);
            return false;
        }
    }

    private String getSecureToken() {
        // Create request payload for token
        Map<String, String> tokenRequest = new HashMap<>();
        tokenRequest.put("institution_id", institutionId);
        tokenRequest.put("cx_user", user);
        tokenRequest.put("cx_password", password);
        tokenRequest.put("cx_reason", "00");
        tokenRequest.put("mac_value", DigestUtils.md5DigestAsHex((institutionId + user).getBytes(StandardCharsets.UTF_8)));

        // Debug log the request details (without sensitive data)
        log.info("Sending token request to URL: {}", apiUrl + "/createtocken24");
        log.info("Token request params: institution_id={}, cx_user={}, cx_reason={}",
                institutionId, user, "00");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(tokenRequest, headers);

        try {
            // Enable detailed logging for HTTP requests in development
//            if (environment.acceptsProfiles(Profiles.of("dev"))) {
                System.setProperty("javax.net.debug", "ssl,handshake");
//
            ResponseEntity<Map> response = restTemplate.exchange(
                    apiUrl + "/createtocken24",
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            // Debug log the response
            log.info("Token request response status: {}", response.getStatusCode());

            Map<String, String> responseBody = response.getBody();
            if (responseBody != null) {
                // Log response body keys for debugging (without sensitive values)
                log.info("Token response contains keys: {}", responseBody.keySet());

                if (responseBody.containsKey("securtoken_24")) {
                    log.info("Successfully obtained security token");
                    return responseBody.get("securtoken_24");
                }
//                else if (responseBody.containsKey("error_code") && responseBody.containsKey("error_msg")) {
//                    String errorMsg = "Payment gateway error: " + responseBody.get("error_code") + " - " + responseBody.get("error_msg");
//                    log.error(errorMsg);
//                    throw new RuntimeException(errorMsg);
//                }
                else {
                    String errorMsg = "Failed to obtain security token. Response does not contain expected token. Response: " + responseBody;
                    log.error(errorMsg);
                    throw new RuntimeException(errorMsg);
                }
            } else {
                String errorMsg = "Failed to obtain security token. Empty response body.";
                log.error(errorMsg);
                throw new RuntimeException(errorMsg);
            }
        } catch (HttpClientErrorException e) {
            // HTTP 4xx error
            log.error("HTTP client error accessing payment gateway: {}, Response: {}",
                    e.getMessage(), e.getResponseBodyAsString(), e);
            throw new RuntimeException("Payment gateway rejected the request: " + e.getMessage(), e);
        } catch (HttpServerErrorException e) {
            // HTTP 5xx error
            log.error("HTTP server error from payment gateway: {}, Response: {}",
                    e.getMessage(), e.getResponseBodyAsString(), e);
            throw new RuntimeException("Payment gateway server error: " + e.getMessage(), e);
        } catch (ResourceAccessException e) {
            // Connection error
            log.error("Connection error accessing payment gateway: {}", e.getMessage(), e);
            throw new RuntimeException("Unable to connect to payment gateway. Please check your network connection or contact support.", e);
        } catch (Exception e) {
            log.error("Error getting security token: {}", e.getMessage(), e);
            throw new RuntimeException("Payment gateway service is currently unavailable. Please try again later.", e);
        }
    }
}
