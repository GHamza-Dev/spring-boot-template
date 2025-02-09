package com.warya.base.sms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsRequest {
    private String login;
    private String password;
    private String oadc;
    private String msisdn_to;
    private String body;
}
