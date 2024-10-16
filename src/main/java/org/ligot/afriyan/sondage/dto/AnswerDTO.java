package org.ligot.afriyan.sondage.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.ligot.afriyan.sondage.enumerations.TypeResponse;
import org.ligot.afriyan.sondage.enumerations.TypeUserSondage;

public class AnswerDTO {
    private Long id;
    private TypeResponse typeResponse;
  private List<ModelResponseDTO> values = new ArrayList<>(0);
    private TypeUserSondage typeUser;

    private Long questionId;
    private Long sondageId;


    private String ip;

    private String userAgent;
    private Instant dateTime;

    public AnswerDTO() {
    }

    public AnswerDTO(Long id) {
        this.id = id;
    }

    public AnswerDTO(Long id, TypeResponse typeResponse, List<ModelResponseDTO> values, TypeUserSondage typeUser) {
        this.id = id;
        this.typeResponse = typeResponse;
        this.values = values;
        this.typeUser = typeUser;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getSondageId() {
        return sondageId;
    }

    public void setSondageId(Long sondageId) {
        this.sondageId = sondageId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeResponse getTypeResponse() {
        return typeResponse;
    }

    public void setTypeResponse(TypeResponse typeResponse) {
        this.typeResponse = typeResponse;
    }

    public List<ModelResponseDTO> getValues() {
        return values;
    }

    public void setValues(List<ModelResponseDTO> values) {
        this.values = values;
    }

    public TypeUserSondage getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(TypeUserSondage typeUser) {
        this.typeUser = typeUser;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AnswerDTO{");
        sb.append("id=").append(id);
        sb.append(", typeResponse=").append(typeResponse);
        sb.append(", values=").append(values);
        sb.append(", typeUser=").append(typeUser);
        sb.append(", questionId=").append(questionId);
        sb.append(", sondageId=").append(sondageId);
        sb.append(", ip='").append(ip).append('\'');
        sb.append(", userAgent='").append(userAgent).append('\'');
        sb.append(", dateTime=").append(dateTime);
        sb.append('}');
        return sb.toString();
    }
}
