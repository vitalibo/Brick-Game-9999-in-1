package com.github.vitalibo.brickgame.core.ui;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class BrickPanelTest {

    private static final int WIDTH = 5;
    private static final int HEIGHT = 10;

    private BrickPanel panel;

    @BeforeMethod
    public void setUp() {
        panel = new BrickPanel(WIDTH, HEIGHT);
    }

    @Test
    public void testBrickSize() {
        Component[] components = panel.getComponents();
        long count = Arrays.stream(components)
            .filter(c -> c instanceof BrickPanel.Brick)
            .count();

        Assert.assertTrue(components.length == count && count == WIDTH * HEIGHT);
    }

    @DataProvider
    public Object[][] samples() {
        return IntStream.range(0, HEIGHT)
            .mapToObj(height -> IntStream.range(0, WIDTH)
                .mapToObj(width -> new Object[]{height, width})
                .toArray(Object[]::new))
            .flatMap(Arrays::stream)
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "samples")
    public void testDraw(int y, int x) {
        boolean[][] src = new boolean[HEIGHT][WIDTH];
        src[y][x] = true;

        panel.draw(src);
        Boolean[] result = Arrays.stream(panel.getComponents())
            .map(BrickPanel.Brick.class::cast)
            .map(BrickPanel.Brick::get)
            .toArray(Boolean[]::new);

        Assert.assertEquals(result, inLine(src));

        src[random(HEIGHT, y)][random(WIDTH, x)] = true;
        Assert.assertNotEquals(result, inLine(src));
    }

    private static int random(int max, Integer... exclude) {
        return new Random().ints(0, max)
            .filter(i -> !Arrays.asList(exclude).contains(i))
            .limit(1)
            .findAny()
            .orElse(-1);
    }

    private static Boolean[] inLine(boolean[][] src) {
        return Arrays.stream(src)
            .flatMap(array -> IntStream.range(0, array.length)
                .mapToObj(i -> array[i])
                .map(Boolean::valueOf))
            .toArray(Boolean[]::new);
    }

}