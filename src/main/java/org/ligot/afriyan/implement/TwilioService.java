package org.ligot.afriyan.implement;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import org.ligot.afriyan.config.swaggerConf.TwilioConfiguration;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class TwilioService {

    private final TwilioConfiguration twilioConfiguration;
    public TwilioService(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
        Twilio.init(twilioConfiguration.getAccountSid(), twilioConfiguration.getAuthToken());
    }

    public Map<String, String> sendSms(Set<String> toNumbers, String message) {
        Map<String, String> mapStatus = new HashMap(0);
        toNumbers.stream()
        .forEach(phone -> {
            Message sms = Message.creator(new PhoneNumber(phone.trim()), new PhoneNumber(twilioConfiguration.getPhoneNumber()), message).create();
        mapStatus.put(phone, sms.getStatus().name());
    });

        return mapStatus;
    }
}
