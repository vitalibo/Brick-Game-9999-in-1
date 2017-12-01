package com.github.vitalibo.brickgame.game.tanks;

import com.github.vitalibo.brickgame.game.Point;
import com.github.vitalibo.brickgame.util.Random;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;

import java.util.function.Consumer;

@AllArgsConstructor
enum Direction implements Consumer<Point> {

    UP(Point::doUp), RIGHT(Point::doRight),
    DOWN(Point::doDown), LEFT(Point::doLeft);

    @Delegate
    private final Consumer<Point> consumer;

    public static Direction random() {
        return values()[Random.nextInt(4)];
    }

}