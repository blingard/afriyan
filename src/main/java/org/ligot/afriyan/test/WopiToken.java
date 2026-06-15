package org.ligot.afriyan.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


public class WopiToken {
    private String fileId;
    private String userId;
    private String userNom;
    private boolean canWrite;
    private LocalDateTime expiry;

    public boolean canWrite() {
        return canWrite;
    }

    public WopiToken() {
    }

    public WopiToken(String fileId, String userId, String userNom, boolean canWrite, LocalDateTime expiry) {
        this.fileId = fileId;
        this.userId = userId;
        this.userNom = userNom;
        this.canWrite = canWrite;
        this.expiry = expiry;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNom() {
        return userNom;
    }

    public void setUserNom(String userNom) {
        this.userNom = userNom;
    }

    public boolean isCanWrite() {
        return canWrite;
    }

    public void setCanWrite(boolean canWrite) {
        this.canWrite = canWrite;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }

    public void setExpiry(LocalDateTime expiry) {
        this.expiry = expiry;
    }
}
