package org.ligot.afriyan.init;

public class SaveListUtils {
    private static Integer TOTAL = 0;
    private static Integer CURRENT = 0;

    public static Integer getTOTAL() {
        return TOTAL;
    }

    public static void setTOTAL(Integer total) {
        TOTAL = total;
    }

    public static Integer getCURRENT() {
        return CURRENT;
    }

    public static void setCURRENT(Integer current) {
        CURRENT = current;
    }

    public static void init(){
        CURRENT = 0;
        TOTAL = 0;
    }
}
