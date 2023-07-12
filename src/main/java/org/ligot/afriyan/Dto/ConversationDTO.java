package org.ligot.afriyan.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDTO {
    private Long id;
    @NotNull
    private String message;
    @NotNull
    private String sender;
    @NotNull
    private String receiver;
    @NotNull
    private String content;
    @NotNull
    private Date dateEnvoie;
}
