package com.warya.base.payment.dto;

import lombok.Data;

@Data
public class PaymentLinkResponse {
    private String statusCode;
    private String status;
    private String orderid;
    private String amount;
    private String transactionDate;
    private String transactionTime;
    private String transactionId;
    private String paymentUrl;
    private String url;
}
