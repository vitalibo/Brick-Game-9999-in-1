package com.github.vitalibo.brickgame.game.snake;

import com.github.vitalibo.brickgame.core.GameException;
import com.github.vitalibo.brickgame.game.Point;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class SnakeTest {

    private Snake snake;

    @BeforeMethod
    public void setUp() {
        snake = Snake.init();
    }

    @Test
    public void testInit() {
        List<Point> expected = Arrays.asList(
            Point.of(0, 0), Point.of(0, 1), Point.of(0, 2));

        List<Point> result = Snake.init();

        Assert.assertEquals(result, expected);
    }

    @Test
    public void testHead() {
        Point expected = Point.of(0, 2);

        Point head = snake.getHead();

        Assert.assertEquals(head, expected);
    }

    @DataProvider
    public Object[][] samples() {
        return new Object[][]{
            {Point.of(0, 2)}, {Point.of(5, 5)}
        };
    }

    @Test(dataProvider = "samples")
    public void testVerifyPassed(Point point) {
        snake = Snake.init();

        snake.verifyEatSelf(point);

        Assert.assertTrue(true);
    }

    @DataProvider
    public Object[][] samplesCrashed() {
        return new Object[][]{
            {Point.of(0, 0)}, {Point.of(0, 1)}
        };
    }

    @Test(dataProvider = "samplesCrashed", expectedExceptions = GameException.class)
    public void testEatSelf(Point point) {
        snake = Snake.init();

        snake.verifyEatSelf(point);
    }

}