package com.github.vitalibo.brickgame.game.race;

import com.github.vitalibo.brickgame.core.GameException;
import com.github.vitalibo.brickgame.game.Point;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CarTest {

    private Car car;

    @BeforeMethod
    public void setUp() {
        car = Car.on(Point.of(5, 5));
    }

    @Test
    public void testDown() {
        Point point = representLikePoint(car);

        car.doDown();
        Point npoint = representLikePoint(car);

        Assert.assertEquals(npoint.getY() - point.getY(), 1);
        Assert.assertEquals(npoint.getX(), point.getX());
    }

    @Test
    public void testLeft() {
        Point point = representLikePoint(car);

        car.doLeft();
        Point npoint = representLikePoint(car);

        Assert.assertEquals(npoint.getY(), point.getY());
        Assert.assertEquals(npoint.getX() - point.getX(), -3);
    }

    @Test
    public void testRight() {
        Point point = representLikePoint(car);

        car.doRight();
        Point npoint = representLikePoint(car);

        Assert.assertEquals(npoint.getY(), point.getY());
        Assert.assertEquals(npoint.getX() - point.getX(), 3);
    }

    @Test(expectedExceptions = GameException.class)
    public void testCarCrashed() {
        car.verifyCrash(Collections.singletonList(car));
    }

    @Test
    public void testVerifyPassed() {
        List<Car> cars = IntStream.range(0, 3)
            .mapToObj(i -> Car.generate())
            .collect(Collectors.toList());

        car.verifyCrash(cars);

        Assert.assertTrue(true);
    }

    private static Point representLikePoint(Car car) {
        return Point.of(max(car, Point::getY), max(car, Point::getX));
    }

    private static int max(Car car, Function<Point, Integer> function) {
        return car.stream()
            .map(function)
            .max(Integer::compareTo)
            .orElseThrow(AssertionError::new);
    }

}