package org.ligot.afriyan.implement;

import kong.unirest.GenericType;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import okhttp3.OkHttpClient;

import org.ligot.afriyan.Dto.SMSDto;
import org.ligot.afriyan.config.swaggerConf.TwilioConfiguration;
import org.ligot.afriyan.echo.dto.ReverseGeocodingResponse;
import org.springframework.stereotype.Component;
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
                    String fullNumber = phone.trim().startsWith(countryCode) ? phone.trim() :  countryCode + phone.trim();

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
                            .addHeader("X-Api-Key", twilioConfiguration.getAccountSid())
                            .addHeader("X-Secret", twilioConfiguration.getAuthToken())
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
                String fullNumber = toNumber.trim().startsWith(countryCode) ? toNumber.trim() :  countryCode + toNumber.trim();
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
                        .addHeader("X-Api-Key", twilioConfiguration.getAccountSid())
                        .addHeader("X-Secret", twilioConfiguration.getAuthToken())
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    mapStatus.put(toNumber, "SENT");
                } else {
                    mapStatus.put(toNumber, "FAILED");
                    System.err.println("Echec de l'envoi vers " + fullNumber + ": " + response.body().string());
                }
            } else {
                throw new Exception("Le numero de telephone 237" + toNumber.trim() + " n'est pas valide");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
/*
    public void sendOneSm(String toNumber, String message) throws Exception {
        try {

            System.err.println(message);
            //Map<String, String> mapStatus = new HashMap(0);
            final String countryCode = "237";
            toNumber = toNumber.trim();
            if (toNumber.length() == 9 & toNumber.startsWith("6")) {
                String fullNumber = toNumber.trim().startsWith(countryCode) ? toNumber.trim() :  countryCode + toNumber.trim();
                SMSDto smsDto = new SMSDto(twilioConfiguration.getSender(), message, fullNumber);
                *//*HttpResponse<String> response = Unirest
                        .post(twilioConfiguration.getUrl())
                        .header("X-Api-Key", twilioConfiguration.getAccountSid())
                        .header("X-Secret", twilioConfiguration.getAuthToken())
                        .header("Content-Type", "application/json")
                        .asString();
                System.err.println(response.getBody());*//*
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
                        .addHeader("X-Api-Key", twilioConfiguration.getAccountSid())
                        .addHeader("X-Secret", twilioConfiguration.getAuthToken())
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    throw new RuntimeException(responseBody.);
                }
            } else {
                throw new Exception("Le numero de telephone " + toNumber.trim() + " n'est pas valide");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/
}
