package com.warya.base.payment;

import com.warya.base.payment.dto.PaymentLinkRequest;
import com.warya.base.payment.dto.PaymentLinkResponse;
import com.warya.base.payment.dto.PaymentStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-link")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@Valid @RequestBody PaymentLinkRequest request) {
        PaymentLinkResponse response = paymentService.createPaymentLink(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{orderid}")
    public ResponseEntity<PaymentStatus> getPaymentStatus(@PathVariable String orderid) {
        PaymentStatus status = paymentService.checkPaymentStatus(orderid);
        return ResponseEntity.ok(status);
    }

    // This endpoint will handle the payment notification callbacks from NAPS
    @PostMapping("/callback")
    public ResponseEntity<Map<String, String>> handlePaymentCallback(@RequestBody Map<String, String> callbackData) {
        boolean success = paymentService.processPaymentCallback(callbackData);

        Map<String, String> response = Map.of("msg", success ? "GATESUCCESS" : "GATEFAILED");
        return ResponseEntity.ok(response);
    }
}
