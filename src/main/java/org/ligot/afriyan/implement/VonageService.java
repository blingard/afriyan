package org.ligot.afriyan.implement;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;

import org.ligot.afriyan.config.swaggerConf.VintageConfiguration;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VonageService {

    private final VintageConfiguration vintage;
    public VonageService(VintageConfiguration vintage) {

        this.vintage = vintage;
        //Twilio.init(twilioConfiguration.getAccountSid(), twilioConfiguration.getAuthToken());
    }

    VonageClient client = VonageClient.builder()
                .apiKey("9fe804d4")
                .apiSecret("kVEec1de0a8Mc1tb")
                .build();

    public void sendSms(Set<String> toNumbers, String message) {
        for (String phone : toNumbers) {
            TextMessage messages = new TextMessage(vintage.getPhoneNumber(), phone, message);

            SmsSubmissionResponse response = client.getSmsClient().submitMessage(messages);

            if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
                System.out.println("Message sent successfully to " + toNumbers);
            } else {
                System.out.println("Message failed to " + toNumbers + " with error: " + response.getMessages().get(0).getErrorText());
        }
    }
    }
}
