package org.ligot.afriyan.config.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileStorageProperties {

    @Value("${file.upload-dir}")
    private String path;

    public String getUploadDir() {
        return path;
    }

}
