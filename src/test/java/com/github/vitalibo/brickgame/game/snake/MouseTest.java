package com.github.vitalibo.brickgame.game.snake;

import com.github.vitalibo.brickgame.game.Point;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MouseTest {

    private static final List<Point> ALL_POINTS;

    static {
        ALL_POINTS = IntStream.range(0, 20)
            .mapToObj(y -> IntStream.range(0, 10)
                .mapToObj(x -> Point.of(y, x)))
            .flatMap(stream -> stream)
            .collect(Collectors.toList());
    }

    private List<Point> points;
    private Mouse mouse;

    @BeforeMethod
    public void setUp() {
        points = new ArrayList<>();
        mouse = Mouse.Generator.init(points);
    }

    @DataProvider
    public Object[][] generator() {
        Random random = new Random();
        return Stream.generate(() -> new ArrayList<>(ALL_POINTS))
            .limit(20)
            .map(list -> {
                Point point = Point.of(
                    random.nextInt(20),
                    random.nextInt(10));
                list.remove(point);
                return new Object[]{list, point};
            })
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "generator")
    public void testNextPoint(List<Point> exclude, Point expected) {
        points.addAll(exclude);

        mouse.generate();
        Point point = mouse.getPoint();

        Assert.assertEquals(point, expected);
    }

    @Test
    public void testEatMouse() {
        Point point = mouse.getPoint();

        boolean result = mouse.verifyEatMouse(point);

        Assert.assertTrue(result);
        Assert.assertNotEquals(mouse.getPoint(), point);
    }

    @Test
    public void testStream() {
        Stream<Point> stream = mouse.stream();

        Assert.assertNotNull(stream);
        Optional<Point> point = stream.findFirst();
        Assert.assertTrue(point.isPresent());
        Assert.assertEquals(point.orElse(null), mouse.getPoint());
    }

}