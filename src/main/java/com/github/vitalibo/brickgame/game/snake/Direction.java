package com.github.vitalibo.brickgame.game.snake;

import java.util.Arrays;

enum Direction {

    LEFT(1), RIGHT(0), UP(3), DOWN(2), NOT_DEFINED(4);

    private int reverse;

    Direction(int reverse) {
        this.reverse = reverse;
    }

    public Direction reverse() {
        return Arrays.stream(values())
            .filter(e -> e.ordinal() == reverse)
            .findFirst()
            .orElseGet(null);
    }

}