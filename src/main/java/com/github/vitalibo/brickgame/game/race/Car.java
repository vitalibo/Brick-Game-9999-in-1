package com.github.vitalibo.brickgame.game.race;

import com.github.vitalibo.brickgame.core.Controllable;
import com.github.vitalibo.brickgame.core.GameException;
import com.github.vitalibo.brickgame.game.Point;
import com.github.vitalibo.brickgame.game.Shape;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Car implements List<Point>, Controllable {

    private static final Shape CAR = Shape.of(3, 0x2, 0x7, 0x2, 0x5);
    private static final Random random = new Random();

    @Delegate
    private final List<Point> car;

    @Override
    public void doDown() {
        forEach(Point::doDown);
    }

    @Override
    public void doLeft() {
        if (get(0).getX() < 4) {
            return;
        }

        IntStream.range(0, 3)
            .forEach(i -> this.forEach(Point::doLeft));
    }

    @Override
    public void doRight() {
        if (get(0).getX() > 5) {
            return;
        }

        IntStream.range(0, 3)
            .forEach(i -> this.forEach(Point::doRight));
    }

    @Override
    public void doUp() {
        throw new UnsupportedOperationException("doUp");
    }

    @Override
    public void doRotate() {
        throw new UnsupportedOperationException("doRotate");
    }

    public void verifyCrash(List<Car> cars) {
        Optional<Point> point = cars.stream()
            .flatMap(Car::stream)
            .filter(this::contains)
            .findFirst();

        if (point.isPresent()) {
            throw new GameException(point.get(), "car crashed");
        }
    }

    public static Car init() {
        return on(Point.of(17, 3));
    }

    public static Car generate() {
        return on(Point.of(-3, random.nextBoolean() ? 3 : 6));
    }

    public static Car on(Point point) {
        List<Point> points = CAR.apply(point)
            .peek(p -> p.doMove(-1, -1))
            .collect(Collectors.toList());

        return new Car(points);
    }

}