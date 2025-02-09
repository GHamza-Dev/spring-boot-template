package com.warya.base.sms.properties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "sms.provider")
public class SmsProperties {
    @NotBlank(message = "API URL must not be blank")
    private String apiUrl;
    
    @NotBlank(message = "Login must not be blank")
    private String login;
    
    @NotBlank(message = "Password must not be blank")
    private String password;
    
    @NotBlank(message = "Sender must not be blank")
    private String sender;
    
    @Min(value = 4, message = "Code length must be at least 4")
    private int codeLength = 6;
    
    @Min(value = 1, message = "Code expiration must be at least 1 minute")
    private long codeExpirationMinutes = 5;
}