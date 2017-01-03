package com.github.vitalibo.brickgame.util;

import com.github.vitalibo.brickgame.game.Point;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CanvasTranslatorTest {

    @DataProvider
    public Object[][] samples() {
        return new Object[][]{
            {0, 0}, {5, 5}, {19, 9}
        };
    }

    @Test(dataProvider = "samples")
    public void testFromStream(int y, int x) {
        Stream<Point> stream = Stream.of(Point.of(y, x));

        boolean[][] arr = CanvasTranslator.from(stream);

        Assert.assertFalse(or(exclude(arr, y, x)));
        Assert.assertTrue(arr[y][x]);
    }


    @Test
    public void testFrom() {
        boolean[][] arr = CanvasTranslator.from(3, 0x2, 0x7, 0x2, 0x5);

        Assert.assertTrue(or(arr[0][1], arr[1][0], arr[1][1], arr[1][2], arr[2][1], arr[3][0], arr[3][2]));
        Assert.assertFalse(or(arr[0][0], arr[2][0], arr[0][2], arr[2][2]));
    }

    private static Boolean[] exclude(boolean[][] canvas, int y, int x) {
        return IntStream.range(0, 20)
            .mapToObj(h -> IntStream.range(0, 10)
                .filter(w -> h != y && w != x)
                .mapToObj(w -> canvas[h][w]))
            .flatMap(s -> s)
            .toArray(Boolean[]::new);
    }

    private static Boolean or(Boolean... values) {
        return Arrays.stream(values)
            .reduce((a, b) -> a || b)
            .orElseThrow(AssertionError::new);
    }

}