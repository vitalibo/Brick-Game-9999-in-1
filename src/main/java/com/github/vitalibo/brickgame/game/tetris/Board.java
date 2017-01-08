package com.github.vitalibo.brickgame.game.tetris;

import com.github.vitalibo.brickgame.game.Point;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Board extends CopyOnWriteArrayList<Point> {

    private static final Random random = new Random();

    private Board(List<Point> points) {
        super(points);
    }

    public void add(List<Point> points) {
        points.forEach(this::add);
    }

    public boolean verify(List<Point> points) {
        return points.stream().noneMatch(this::contains);
    }

    public int cleanUp() {
        return (int) IntStream.range(0, 20)
            .filter(i -> stream()
                .filter(point -> point.getY() == i)
                .count() == 10)
            .peek(i -> stream()
                .filter(point -> point.getY() == i)
                .forEach(this::remove))
            .peek(i -> stream()
                .filter(point -> point.getY() < i)
                .forEach(Point::doDown))
            .count();
    }

    public boolean isFull() {
        return stream().anyMatch(point -> point.getY() == -1);
    }

    public static Board init(int level) {
        return new Board(IntStream.rangeClosed(20 - level, 19)
            .mapToObj(y -> IntStream.range(0, 10)
                .filter(i -> random.nextBoolean())
                .mapToObj(x -> Point.of(y, x)))
            .flatMap(stream -> stream)
            .collect(Collectors.toList()));
    }

}