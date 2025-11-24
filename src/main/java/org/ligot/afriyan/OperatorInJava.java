package org.ligot.afriyan;

public class OperatorInJava {
    public static void main(String[] args) {
        boolean canine = true, wolf = true;
        int teeth = 20;
        canine = (teeth != 10) ^ (wolf=false);
        System.out.println(canine+", "+teeth+", "+wolf);

    }
}
