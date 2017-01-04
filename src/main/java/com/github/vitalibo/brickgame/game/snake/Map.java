package com.github.vitalibo.brickgame.game.snake;

import com.github.vitalibo.brickgame.core.GameException;
import com.github.vitalibo.brickgame.game.Point;
import com.github.vitalibo.brickgame.game.Shape;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(staticName = "of")
class Map implements List<Point> {

    private static final String MAP_RESOURCE = "game/snake/%02d.map";

    @Delegate
    private final List<Point> points;

    public void verifyCrashOnBorder(Point head) {
        boolean match = points.stream()
            .anyMatch(head::equals);

        if (match) {
            throw new GameException(head, "snake crash init map border");
        }
    }

    public static Map of(int level) {
        List<Point> points = Shape.of(String.format(MAP_RESOURCE, level))
            .apply(Point.of(0, 0))
            .collect(Collectors.toList());

        return new Map(Collections.unmodifiableList(points));
    }

}