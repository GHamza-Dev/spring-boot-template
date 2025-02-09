package com.warya.base.sms.util;

import com.warya.base.sms.exception.SmsServiceException;
import com.warya.base.sms.model.SmsResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.StringReader;

@Log4j2
@Component
public class XmlUtil {
    private final JAXBContext jaxbContext;

    public XmlUtil() throws JAXBException {
        this.jaxbContext = JAXBContext.newInstance(SmsResponse.class);
    }

    public SmsResponse parseXmlResponse(String xml) {
        try {
            log.debug("Parsing XML response: {}", xml);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xml);
            SmsResponse response = (SmsResponse) unmarshaller.unmarshal(reader);
            log.debug("Successfully parsed XML response. Status code: {}", response.getStatusCode());
            return response;
        } catch (JAXBException e) {
            log.error("Failed to parse XML response", e);
            throw new SmsServiceException("Failed to parse SMS provider response", e);
        }
    }
}