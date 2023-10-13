package org.ligot.afriyan.Dto;

import org.springframework.web.multipart.MultipartFile;

public class MediatechRequest {
    private MultipartFile file;
    private String text;

    public MediatechRequest() {
    }

    public MediatechRequest(MultipartFile file, String text) {
        this.file = file;
        this.text = text;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
