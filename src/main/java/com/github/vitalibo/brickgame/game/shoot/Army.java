package com.github.vitalibo.brickgame.game.shoot;

import com.github.vitalibo.brickgame.game.Point;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

class Army implements List<Point> {

    private static final Random random = new Random();

    @Delegate
    private final List<Point> points;

    Army() {
        this.points = new CopyOnWriteArrayList<>();
    }

    boolean killEnemy(List<Point> shoots) {
        List<Point> o = new ArrayList<>(shoots);
        shoots.removeAll(points);
        return points.removeAll(o);
    }

    void makeAttack() {
        points.forEach(Point::doDown);

        IntStream.range(0, 10)
            .filter(i -> random.nextBoolean())
            .forEach(i -> add(Point.of(0, i)));
    }

    boolean hasWin() {
        return points.stream()
            .anyMatch(o -> o.getY() > 19);
    }

}
