package com.warya.base.sms.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class SmsResponse {
    @XmlElement(name = "statuscode")
    private int statusCode;
    
    @XmlElement(name = "message")
    private String message;
    
    public boolean isSuccess() {
        return statusCode == 0;
    }
}
