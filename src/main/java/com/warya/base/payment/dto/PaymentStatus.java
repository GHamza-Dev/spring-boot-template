package com.warya.base.payment.dto;

import lombok.Data;

@Data
public class PaymentStatus {
    private String status;
    private String statusCode;
    private String authStatusCode;
    private String authStatus;
    private String orderid;
    private String amount;
    private String transactionDate;
    private String transactionTime;
    private String authnumber;
    private String transactionId;
}
