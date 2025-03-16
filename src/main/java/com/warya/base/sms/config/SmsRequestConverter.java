package com.warya.base.sms.config;

import com.warya.base.sms.model.SmsRequest;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SmsRequestConverter implements HttpMessageConverter<SmsRequest> {
    
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }
    
    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return SmsRequest.class.isAssignableFrom(clazz) && 
               MediaType.APPLICATION_FORM_URLENCODED.includes(mediaType);
    }
    
    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED);
    }
    
    @Override
    public SmsRequest read(Class<? extends SmsRequest> clazz, HttpInputMessage inputMessage) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void write(SmsRequest request, MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("login", request.getLogin());
        formData.add("password", request.getPassword());
        formData.add("oadc", request.getOadc());
        formData.add("msisdn_to", request.getMsisdn_to());
        formData.add("body", request.getBody());
        
        String formString = formData.entrySet().stream()
            .map(entry -> entry.getKey() + "=" + URLEncoder.encode(entry.getValue().get(0), StandardCharsets.UTF_8))
            .collect(Collectors.joining("&"));
        
        outputMessage.getHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        try (OutputStream os = outputMessage.getBody()) {
            os.write(formString.getBytes(StandardCharsets.UTF_8));
        }
    }
}