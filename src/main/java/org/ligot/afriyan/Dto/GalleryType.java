package org.ligot.afriyan.Dto;

import java.util.Arrays;
import java.util.Objects;

public enum GalleryType {

    VIDEO(new String[]{"mp4", "avi", "mkv", "mov", "wmv", "flv", "webm", "mpeg", "mpg", "3gp"}),
    AUDIO(new String[]{"mp3", "wav", "aac", "flac", "ogg", "wma", "m4a", "amr"}),
    IMAGE(new String[]{"png", "jpg", "jpeg", "gif", "bmp", "webp", "tiff", "svg", "ico"});

    private final String[] extensions;

    GalleryType(String[] extensions) {
        this.extensions = extensions;
    }

    public String[] getExtensions() {
        return extensions;
    }

    public void check(String extension){
        if(Objects.isNull(extension))
            throw new IllegalArgumentException("Extension must not be null");
        if(!Arrays.stream(this.extensions).toList().contains(extension))
            throw new IllegalArgumentException("Extension not valid");
    }
}
