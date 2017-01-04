package com.github.vitalibo.brickgame.game.snake;

import com.github.vitalibo.brickgame.game.Point;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

class Mouse {

    private final Generator generator;

    @Getter
    private Point point;

    private Mouse(Generator generator) {
        this.generator = generator;
        this.point = Point.of(generator.nextPoint());
    }

    public void generate() {
        point = Point.of(generator.nextPoint());
    }

    public Stream<Point> stream() {
        return Stream.of(point);
    }

    public boolean verifyEatMouse(Point head) {
        if (!head.equals(point)) {
            return false;
        }

        point = Point.of(generator.nextPoint());
        return true;
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Generator {

        private static final Random random = new Random();

        private final List<Point>[] exclude;
        private final Point newPoint;

        private Point nextPoint() {
            newPoint.setX(random.nextInt(10));
            newPoint.setY(random.nextInt(20));

            for (List<Point> points : exclude) {
                if (points.contains(newPoint)) {
                    return nextPoint();
                }
            }

            return newPoint;
        }

        @SafeVarargs
        public static Mouse init(List<Point>... exclude) {
            return new Mouse(new Generator(exclude, new Point()));
        }

    }

}
