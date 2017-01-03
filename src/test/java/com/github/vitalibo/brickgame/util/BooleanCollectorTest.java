package com.github.vitalibo.brickgame.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.stream.IntStream;

public class BooleanCollectorTest {

    @Test
    public void testToArray() {
        boolean[] result = IntStream.range(0, 5)
            .mapToObj(i -> i % 2 == 0)
            .collect(BooleanCollector.toArray());

        Assert.assertEquals(result, new boolean[]{true, false, true, false, true});
    }

    @Test
    public void testToTwoDimensionalArray() throws Exception {
        boolean[][] result = IntStream.range(0, 2)
            .mapToObj(i -> new boolean[]{true, false})
            .collect(BooleanCollector.toTwoDimensionalArray());

        Assert.assertEquals(result, new boolean[][]{{true, false}, {true, false}});
    }

}