package com.github.vitalibo.brickgame.game.race;

import com.github.vitalibo.brickgame.core.GameException;
import com.github.vitalibo.brickgame.game.Point;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Road implements List<Point> {

    @Delegate
    private final List<Point> points;
    @Getter
    private int traffic;

    public void doDown() {
        for (Point point : this) {
            point.doDown();
            try {
                point.verify();
            } catch (GameException e) {
                point.setY(0);
            }
        }

        traffic++;
    }

    public static Road init() {
        List<Point> points = IntStream.range(0, 20)
            .filter(i -> i % 4 != 0)
            .mapToObj(i -> Stream.of(Point.of(i, 0), Point.of(i, 9)))
            .flatMap(stream -> stream)
            .collect(Collectors.toList());

        return new Road(new CopyOnWriteArrayList<>(points));
    }

}
