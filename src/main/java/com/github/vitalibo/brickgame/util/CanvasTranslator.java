package com.github.vitalibo.brickgame.util;

import com.github.vitalibo.brickgame.game.Point;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Stream;

@UtilityClass
public final class CanvasTranslator {

    @SafeVarargs
    public static boolean[][] from(Stream<Point>... points) {
        boolean[][] canvas = new boolean[20][10];
        Arrays.stream(points)
            .flatMap(stream -> stream)
            .forEach(point -> {
                try {
                    canvas[point.getY()][point.getX()] = true;
                } catch (IndexOutOfBoundsException ignored) {
                }
            });

        return canvas;
    }

    public static boolean[][] from(int width, int... values) {
        return Arrays.stream(values)
            .mapToObj(o -> convertToBinary(width, o))
            .map(b -> Arrays.stream(b)
                .map(i -> i == 1)
                .collect(BooleanCollector.toArray()))
            .collect(BooleanCollector.toTwoDimensionalArray());
    }

    private static Integer[] convertToBinary(int capacity, int value) {
        Integer[] binary = new Integer[capacity];
        for (int i = capacity - 1, num = value; i >= 0; i--, num >>>= 1) {
            binary[i] = num & 1;
        }

        return binary;
    }

    public static boolean[][] from(String resource) {
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(resource);
        return new BufferedReader(new InputStreamReader(stream))
            .lines()
            .map(CharSequence::chars)
            .map(chars -> chars
                .mapToObj(i -> Character.toChars(i)[0] != '0')
                .collect(BooleanCollector.toArray()))
            .collect(BooleanCollector.toTwoDimensionalArray());
    }

}