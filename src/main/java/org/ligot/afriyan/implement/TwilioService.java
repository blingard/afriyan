package org.ligot.afriyan.implement;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import org.ligot.afriyan.config.swaggerConf.TwilioConfiguration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class TwilioService {

    private final TwilioConfiguration twilioConfiguration;
    public TwilioService(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
        Twilio.init(twilioConfiguration.getAccountSid(), twilioConfiguration.getAuthToken());
    }

    public Map<String, String> sendSms(Set<String> toNumbers, String message) {
        Map<String, String> mapStatus = new HashMap(0);
        final String countryCode = "237";
        toNumbers.stream()
        .forEach(phone -> {
            try {
                if(phone.length()==9 & phone.startsWith("6")){
                    Message sms = Message.creator(
                                    new PhoneNumber(countryCode+phone.trim()),
                                    new PhoneNumber(twilioConfiguration.getPhoneNumber()),
                                    message)
                            .create();
                    mapStatus.put(phone, sms.getStatus().name());
                }else
                    throw new Exception("Le numero de telephone 237"+phone.trim()+" n'est pas valide");
            }catch (Exception ex){
                ex.printStackTrace();
            }
    });
        return mapStatus;
    }

    public void sendOneSms(String toNumber, String message) {
        try {

            Map<String, String> mapStatus = new HashMap(0);
            final String countryCode = "237";
            if(toNumber.length()==9 & toNumber.startsWith("6")){
                Message sms = Message.creator(
                                new PhoneNumber(countryCode+toNumber.trim()),
                                new PhoneNumber(twilioConfiguration.getPhoneNumber().trim()),
                                message)
                        .create();
                mapStatus.put(toNumber, sms.getStatus().name());
            }else
                throw new Exception("Le numero de telephone 237"+toNumber.trim()+" n'est pas valide");

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
