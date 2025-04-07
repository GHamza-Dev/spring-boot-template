package com.warya.base.payment;

import com.warya.base.application.entity.Adherant;
import com.warya.base.payment.dto.PaymentLinkRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentUtilService {
    @Value("${app.payment.currency}")
    private String currency;
    @Value("${app.payment.registration.fee}")
    private Double regenerationFee;

    public String generatePaymentOrderRef(String prefix) {
        long timestamp = System.currentTimeMillis();

        int randomNum = (int) (Math.random() * 900000) + 100000;

        return prefix + timestamp + randomNum;
    }

    public PaymentLinkRequest initiatePaymentLinkRequest(Adherant adherant, String orderId) {
        return PaymentLinkRequest.builder()
                .amount(regenerationFee)
                .currency(currency)
                .orderReference(orderId)
                .firstName(adherant.getNom())
                .lastName(adherant.getPrenom())
                .email(adherant.getAdresseEmail())
                .phone(adherant.getPortable())
                .build();
    }
}
