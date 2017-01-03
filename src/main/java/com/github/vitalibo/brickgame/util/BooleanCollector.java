package com.github.vitalibo.brickgame.util;

import lombok.experimental.UtilityClass;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public final class BooleanCollector {

    public static Collector<Boolean, ?, boolean[]> toArray() {
        return Collectors.collectingAndThen(Collectors.toList(), list -> {
            boolean[] array = new boolean[list.size()];
            IntStream.range(0, list.size())
                .forEach(i -> array[i] = list.get(i));
            return array;
        });
    }

    public static Collector<boolean[], ?, boolean[][]> toTwoDimensionalArray() {
        return Collectors.collectingAndThen(Collectors.toList(), list -> {
            boolean[][] array = new boolean[list.size()][];
            IntStream.range(0, list.size())
                .forEach(i -> array[i] = list.get(i));
            return array;
        });
    }

}