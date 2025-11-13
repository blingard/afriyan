package org.ligot.afriyan.Dto;

public class SMSDto {
    private String senderId;
    private String message;
    private String msisdn;
    private String flag="UCS2";
    private boolean maskedMsisdn=false;

    public SMSDto() {
    }

    public SMSDto(String senderId, String message, String msisdn) {
        this.senderId = senderId;
        this.message = message;
        this.msisdn = msisdn;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean isMaskedMsisdn() {
        return maskedMsisdn;
    }

    public void setMaskedMsisdn(boolean maskedMsisdn) {
        this.maskedMsisdn = maskedMsisdn;
    }
}
