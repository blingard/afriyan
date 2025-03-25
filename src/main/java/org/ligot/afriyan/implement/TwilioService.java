package org.ligot.afriyan.implement;

import com.twilio.type.PhoneNumber;

import okhttp3.OkHttpClient;

import org.ligot.afriyan.config.swaggerConf.TwilioConfiguration;
import org.springframework.stereotype.Component;
import com.twilio.rest.api.v2010.account.Message;
import okhttp3.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class TwilioService {

    private final TwilioConfiguration twilioConfiguration;

    OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
    MediaType mediaType = MediaType.parse("application/json");

    public TwilioService(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
        // Twilio.init(twilioConfiguration.getAccountSid(),
        // twilioConfiguration.getAuthToken());
    }

    public Map<String, String> sendSms(Set<String> toNumbers, String message) {
        Map<String, String> mapStatus = new HashMap<>(0);
        final String countryCode = "237";
        OkHttpClient client = new OkHttpClient();

        toNumbers.forEach(phone -> {
            try {
                if (phone.length() == 9 && phone.startsWith("6")) {
                    String fullNumber = countryCode + phone.trim();

                    String jsonBody = "{\n" +
                            "    \"senderId\": \"" + twilioConfiguration.getSender() + "\",\n" +
                            "    \"message\": \"" + message + "\",\n" +
                            "    \"msisdn\": [\"" + fullNumber + "\"],\n" +
                            "    \"flag\": \"UCS2\",\n" +
                            "    \"maskedMsisdn\": false\n" +
                            "}";

                    MediaType mediaType = MediaType.parse("application/json");
                    RequestBody body = RequestBody.create(mediaType, jsonBody);

                    Request request = new Request.Builder()
                            .url(twilioConfiguration.getUrl())
                            .method("POST", body)
                            .addHeader("X-Api-Key", twilioConfiguration.getAccountSid()) // À remplacer par la vraie clé
                            .addHeader("X-Secret", twilioConfiguration.getAuthToken()) // À remplacer par le vrai secret
                            .addHeader("Content-Type", "application/json")
                            .build();

                    Response response = client.newCall(request).execute();

                    if (response.isSuccessful()) {
                        mapStatus.put(phone, "SENT");
                    } else {
                        mapStatus.put(phone, "FAILED");
                        System.err.println("Echec de l'envoi vers " + phone + ": " + response.body().string());
                    }
                } else {
                    throw new Exception("Le numero de telephone 237" + phone.trim() + " n'est pas valide");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                mapStatus.put(phone, "ERROR");
            }
        });

        return mapStatus;
    }

    /*
     * public Map<String, String> sendSms(Set<String> toNumbers, String message) {
     * Map<String, String> mapStatus = new HashMap(0);
     * List<String> listNumero;
     * final String countryCode = "237";
     * toNumbers.stream()
     * .forEach(phone -> {
     * try {
     * if (phone.length() == 9 & phone.startsWith("6")) {
     * Message sms = Message.creator(
     * new PhoneNumber(countryCode + phone.trim()),
     * new PhoneNumber(twilioConfiguration.getPhoneNumber()),
     * message)
     * .create();
     * mapStatus.put(phone, sms.getStatus().name());
     * } else
     * throw new Exception("Le numero de telephone 237" + phone.trim() +
     * " n'est pas valide");
     * } catch (Exception ex) {
     * ex.printStackTrace();
     * }
     * });
     * return mapStatus;
     * }
     */

    public void sendOneSms(String toNumber, String message) {
        try {

            Map<String, String> mapStatus = new HashMap(0);
            final String countryCode = "237";
            toNumber = toNumber.trim();
            if (toNumber.length() == 9 & toNumber.startsWith("6")) {
                String jsonBody = "{\n" +
                        "    \"senderId\": \"" + twilioConfiguration.getSender() + "\",\n" +
                        "    \"message\": \"" + message + "\",\n" +
                        "    \"msisdn\": [\"" + toNumber + "\"],\n" +
                        "    \"flag\": \"UCS2\",\n" +
                        "    \"maskedMsisdn\": false\n" +
                        "}";

                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, jsonBody);

                Request request = new Request.Builder()
                        .url(twilioConfiguration.getUrl())
                        .method("POST", body)
                        .addHeader("X-Api-Key", twilioConfiguration.getAccountSid()) // À remplacer par la vraie clé
                        .addHeader("X-Secret", twilioConfiguration.getAuthToken()) // À remplacer par le vrai secret
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    mapStatus.put(toNumber, "SENT");
                } else {
                    mapStatus.put(toNumber, "FAILED");
                    System.err.println("Echec de l'envoi vers " + toNumber + ": " + response.body().string());
                }
            } else {
                throw new Exception("Le numero de telephone 237" + toNumber.trim() + " n'est pas valide");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendOneSm(String toNumber, String message) throws Exception {
        try {

            Map<String, String> mapStatus = new HashMap(0);
            final String countryCode = "237";
            toNumber = toNumber.trim();
            if (toNumber.length() == 9 & toNumber.startsWith("6")) {
                String jsonBody = "{\n" +
                        "    \"senderId\": \"" + twilioConfiguration.getSender() + "\",\n" +
                        "    \"message\": \"" + message + "\",\n" +
                        "    \"msisdn\": [\"" + toNumber + "\"],\n" +
                        "    \"flag\": \"UCS2\",\n" +
                        "    \"maskedMsisdn\": false\n" +
                        "}";

                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, jsonBody);

                Request request = new Request.Builder()
                        .url(twilioConfiguration.getUrl())
                        .method("POST", body)
                        .addHeader("X-Api-Key", twilioConfiguration.getAccountSid()) // À remplacer par la vraie clé
                        .addHeader("X-Secret", twilioConfiguration.getAuthToken()) // À remplacer par le vrai secret
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    mapStatus.put(toNumber, "SENT");
                } else {
                    mapStatus.put(toNumber, "FAILED");
                    System.err.println("Echec de l'envoi vers " + toNumber + ": " + response.body().string());
                }
            } else {
                throw new Exception("Le numero de telephone 237" + toNumber.trim() + " n'est pas valide");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
