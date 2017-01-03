package com.github.vitalibo.brickgame.core.ui;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import static com.github.vitalibo.brickgame.core.ui.NumberPanel.Digit;

public class DigitTest {

    private Digit spyDigit;

    @BeforeMethod
    public void setUp() {
        spyDigit = Mockito.spy(Digit.class);
    }

    @DataProvider
    public Object[][] samples() {
        return IntStream.range(0, 10)
            .mapToObj(i -> new Object[]{i})
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "samples")
    public void testRepaint(int value) {
        spyDigit.set(random(value));
        Mockito.reset(spyDigit);

        spyDigit.set(value);

        Mockito.verify(spyDigit).repaint();
    }

    @Test
    public void testNotRepaint() {
        spyDigit.set(5);
        Mockito.reset(spyDigit);

        spyDigit.set(5);

        Mockito.verify(spyDigit, Mockito.never()).repaint();
    }

    @Test(dataProvider = "samples")
    public void testPaintDigit(int value) {
        Graphics mockGraphics = Mockito.mock(Graphics.class);
        Mockito.when(mockGraphics.create()).thenReturn(mockGraphics);

        spyDigit.set(value);
        spyDigit.paintComponent(mockGraphics);

        Mockito.verify(mockGraphics).drawImage(
            Mockito.notNull(), Mockito.eq(0), Mockito.eq(0), Mockito.eq(null));
    }

    @DataProvider
    public Object[][] samplesIllegalValue() {
        return new Object[][]{
            {-1}, {10}
        };
    }

    @Test(dataProvider = "samplesIllegalValue", expectedExceptions = IllegalArgumentException.class)
    public void testSetIllegalValue(int value) {
        spyDigit.set(value);
    }

    private static int random(Integer... exclude) {
        return new Random()
            .ints(0, 10)
            .filter(i -> !Arrays.asList(exclude).contains(i))
            .findFirst()
            .orElse(-1);
    }

}