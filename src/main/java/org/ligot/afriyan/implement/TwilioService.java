package org.ligot.afriyan.implement;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import org.ligot.afriyan.config.swaggerConf.TwilioConfiguration;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;

import java.util.Set;

@Service
public class TwilioService {

    private final TwilioConfiguration twilioConfiguration;
    public TwilioService(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
        Twilio.init(twilioConfiguration.getAccountSid(), twilioConfiguration.getAuthToken());
    }

    public void sendSms(Set<String> toNumbers, String message) {
        for (String phone : toNumbers) {

            Message.creator(new PhoneNumber(phone), new PhoneNumber(twilioConfiguration.getPhoneNumber()), message).create();
        }
    }
}
