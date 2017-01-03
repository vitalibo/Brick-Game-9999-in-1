package com.github.vitalibo.brickgame.game;

import com.github.vitalibo.brickgame.core.GameException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PointTest {

    @DataProvider
    public Object[][] samplesInBounds() {
        return new Object[][]{
            {0, 0}, {19, 9}
        };
    }

    @Test(dataProvider = "samplesInBounds")
    public void testVerifyPassed(int y, int x) {
        Point point = Point.of(y, x);

        point.verify();

        Assert.assertTrue(true);
    }

    @DataProvider
    public Object[][] samplesOutOfBounds() {
        return new Object[][]{
            {-1, 0}, {0, -1}, {20, 0}, {0, 10}
        };
    }

    @Test(dataProvider = "samplesOutOfBounds", expectedExceptions = GameException.class)
    public void testVerifyFailed(int y, int x) {
        Point point = Point.of(y, x);

        point.verify();
    }

    @DataProvider
    public Object[][] samplesMoveVertical() {
        return new Object[][]{
            {1, 0}, {19, 18}
        };
    }

    @Test(dataProvider = "samplesMoveVertical")
    public void testMoveUp(int y, int e) {
        Point point = Point.of(y, 0);
        Point expected = Point.of(e, 0);

        point.doUp();

        Assert.assertEquals(point, expected);
    }

    @Test(dataProvider = "samplesMoveVertical")
    public void testMoveDown(int e, int y) {
        Point point = Point.of(y, 0);
        Point expected = Point.of(e, 0);

        point.doDown();

        Assert.assertEquals(point, expected);
    }

    @DataProvider
    public Object[][] samplesMoveHorizontal() {
        return new Object[][]{
            {1, 0}, {9, 8}
        };
    }

    @Test(dataProvider = "samplesMoveHorizontal")
    public void testMoveLeft(int x, int e) {
        Point point = Point.of(0, x);
        Point expected = Point.of(0, e);

        point.doLeft();

        Assert.assertEquals(point, expected);
    }

    @Test(dataProvider = "samplesMoveHorizontal")
    public void testMoveRight(int e, int x) {
        Point point = Point.of(0, x);
        Point expected = Point.of(0, e);

        point.doRight();

        Assert.assertEquals(point, expected);
    }

}