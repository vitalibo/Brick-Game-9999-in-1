package com.github.vitalibo.brickgame.game;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class ShapeTest {

    private static final Point POINT = Point.of(0, 0);

    @DataProvider
    public Object[][] samples() {
        return new Object[][]{
            {
                Shape.of(3, 0x2, 0x7, 0x2, 0x5),
                Shape.of(new int[][]{{0, 1, 0}, {1, 1, 1}, {0, 1, 0}, {1, 0, 1}})},
            {
                Shape.of(4, 0xF, 0x9, 0x9, 0xF),
                Shape.of(new int[][]{{1, 1, 1, 1}, {1, 0, 0, 1}, {1, 0, 0, 1}, {1, 1, 1, 1}})}
        };
    }

    @Test(dataProvider = "samples")
    public void testShapeEquals(Shape shape, Shape shape1) {
        List<Point> list = shape.apply(POINT).collect(Collectors.toList());

        List<Point> list1 = shape1.apply(POINT).collect(Collectors.toList());

        Assert.assertEquals(list, list1);
    }

}