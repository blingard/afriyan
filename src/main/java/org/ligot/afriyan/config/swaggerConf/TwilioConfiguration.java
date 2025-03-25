package org.ligot.afriyan.config.swaggerConf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TwilioConfiguration {
    @Value("${lmt.accountSid}")
    private String accountSid;
    @Value("${lmt.authToken}")
    private String authToken;
    @Value("${lmt.sender}")
    private String phoneNumber;
    @Value("${lmt.url}")
    private String url;

    @Value("${lmt.sender}")
    private String sender;

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

}
