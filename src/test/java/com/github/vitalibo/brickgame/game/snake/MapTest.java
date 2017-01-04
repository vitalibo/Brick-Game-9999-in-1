package com.github.vitalibo.brickgame.game.snake;

import com.github.vitalibo.brickgame.core.GameException;
import com.github.vitalibo.brickgame.game.Point;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.stream.IntStream;

public class MapTest {

    private Map map;

    @BeforeMethod
    public void setUp() {
        map = Map.of(99);
    }

    @DataProvider
    public Object[][] levels() {
        return IntStream.range(0, 16)
            .mapToObj(i -> new Object[]{i})
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "levels")
    public void testLoadingMap(int level) {
        map = Map.of(level);

        Assert.assertNotNull(map);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testUnmodifiableList() {
        map.add(Point.of(0, 0));
    }

    @DataProvider
    public Object[][] samples() {
        return new Object[][]{
            {0, 1}, {1, 0}, {1, 2}, {2, 1}
        };
    }

    @Test(dataProvider = "samples")
    public void testVerifyPassed(int y, int x) {
        map.verifyCrashOnBorder(Point.of(y, x));

        Assert.assertTrue(true);
    }

    @DataProvider
    public Object[][] samplesCrashed() {
        return new Object[][]{
            {0, 0}, {1, 1}, {0, 2}, {2, 0}
        };
    }

    @Test(dataProvider = "samplesCrashed", expectedExceptions = GameException.class)
    public void testCrashOnBorder(int y, int x) {
        map.verifyCrashOnBorder(Point.of(y, x));
    }

}