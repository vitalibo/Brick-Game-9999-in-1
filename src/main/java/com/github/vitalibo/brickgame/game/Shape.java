package com.github.vitalibo.brickgame.game;

import com.github.vitalibo.brickgame.util.CanvasTranslator;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class Shape implements Function<Point, Stream<Point>> {

    private final Point[] shape;

    private Shape(Point[] shape) {
        this.shape = shape;
    }

    @Override
    public Stream<Point> apply(Point point) {
        return Arrays.stream(shape)
            .map(Point::of)
            .peek(p -> p.doMove(point.getY(), point.getX()));
    }

    public static Shape of(int[][] shape) {
        return new Shape(IntStream.range(0, shape.length)
            .mapToObj(y -> IntStream.range(0, shape[y].length)
                .filter(x -> shape[y][x] != 0)
                .mapToObj(x -> Point.of(y, x)))
            .flatMap(stream -> stream)
            .toArray(Point[]::new));
    }

    public static Shape of(int width, int... values) {
        return of(CanvasTranslator.from(width, values));
    }

    public static Shape of(String resource) {
        return of(CanvasTranslator.from(resource));
    }

    public static Shape of(boolean[][] shape) {
        return new Shape(IntStream.range(0, shape.length)
            .mapToObj(y -> IntStream.range(0, shape[y].length)
                .filter(x -> shape[y][x])
                .mapToObj(x -> Point.of(y, x)))
            .flatMap(stream -> stream)
            .toArray(Point[]::new));
    }

}