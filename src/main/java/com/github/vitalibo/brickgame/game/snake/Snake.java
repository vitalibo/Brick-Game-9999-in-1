package com.github.vitalibo.brickgame.game.snake;

import com.github.vitalibo.brickgame.core.GameException;
import com.github.vitalibo.brickgame.game.Point;
import com.github.vitalibo.brickgame.util.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor(staticName = "init")
class Snake implements List<Point> {

    @Delegate
    private final List<Point> body;

    public Point getHead() {
        Point head = body.get(body.size() - 1);
        return Builder.of(head)
            .map(Point::of)
            .with(body::add)
            .get();
    }

    public void verifyEatSelf(Point head) {
        IntStream.range(0, body.size() - 1)
            .mapToObj(body::get)
            .filter(head::equals)
            .findFirst()
            .ifPresent(point -> {
                throw new GameException(point, "snake ate itself");
            });
    }

    public static Snake init() {
        List<Point> body = IntStream.range(0, 3)
            .mapToObj(i -> Point.of(0, i))
            .collect(Collectors.toList());

        return new Snake(body);
    }

}