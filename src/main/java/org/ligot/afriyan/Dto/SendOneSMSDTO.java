package org.ligot.afriyan.Dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import org.ligot.afriyan.entities.Status;

import java.util.HashSet;
import java.util.Set;

public class SendOneSMSDTO {
    @NotNull
    private String phone;
    @NotNull
    private String message;

    public SendOneSMSDTO() {
    }

    public SendOneSMSDTO(@NotNull String phone, @NotNull String message) {
        this.phone = phone;
        this.message = message;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SendOneSMSDTO{" +
                "phone='" + phone + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
