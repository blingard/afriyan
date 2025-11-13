package org.ligot.afriyan.echo;

import java.text.Normalizer;

public class AccentUtils {
    public static String normalize(String input) {
        if (input == null) return null;
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", "").toLowerCase();
    }
}
