package org.ligot.afriyan.implement;

import java.util.Random;

public class Utils {
    private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA = "aqwsderfgtyhjuiklopzxcvbnm1234567890@#$%&_";
    protected static final String NUM = "0123456789";
    protected static String genCode(String entity, int size){
        StringBuilder sb = new StringBuilder(entity);
        String set = ALPHA_CAPS+NUM; // characters to choose from
        int low = 0;
        int high = set.length();
        for (int i= 0; i < size; i++) {
            Random r = new Random();
            int result = r.nextInt(high-low) + low;
            sb.append(set.charAt(result));
        }
        return sb.toString();
    }

    protected static String genCode(){
        StringBuilder sb = new StringBuilder();
        String set = ALPHA; // characters to choose from
        int low = 0;
        int high = set.length();
        for (int i= 0; i < 16; i++) {
            Random r = new Random();
            int result = r.nextInt(high-low) + low;
            sb.append(set.charAt(result));
        }
        return sb.toString();
    }
}
