package org.ligot.afriyan.config.swaggerConf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VintageConfiguration {
     @Value("${apiKey}")
    private String apiKey;
    @Value("${apiSecret}")
    private String apiSecret;
    @Value("${vinagePhone}")
    private String phoneNumber;
    public String getApiKey() {
        return apiKey;
    }
    public String getApiSecret() {
        return apiSecret;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

}
