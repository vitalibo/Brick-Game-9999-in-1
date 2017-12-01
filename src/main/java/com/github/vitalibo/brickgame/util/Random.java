package com.github.vitalibo.brickgame.util;

public class Random {

    private static final java.util.Random random = new java.util.Random();

    public static int nextInt(int bound) {
        return random.nextInt(bound);
    }

}
