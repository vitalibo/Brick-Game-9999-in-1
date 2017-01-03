package com.github.vitalibo.brickgame.core.ui;

import com.github.vitalibo.brickgame.core.Number;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NumberPanelTest {

    private NumberPanel panel;

    @DataProvider
    public Object[][] samplesDigitSequence() {
        return new Object[][]{
            {10, 0, Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)},
            {10, 1, Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 1)},
            {10, 1234567890, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)}
        };
    }

    @Test(dataProvider = "samplesDigitSequence")
    public void testDigitSequence(int capacity, int value, List<Integer> expected) {
        panel = new NumberPanel(capacity);

        panel.set(value);

        List<Integer> sequence = Arrays.stream(panel.getComponents())
            .map(c -> (Number) c)
            .map(Number::get)
            .collect(Collectors.toList());
        Assert.assertEquals(sequence, expected);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSetIllegalValue() {
        panel = new NumberPanel(3, 999);

        panel.set(-1);
    }

    @DataProvider
    public Object[][] samplesMaxValue() {
        return new Object[][]{
            {10}, {99}, {100}, {999}
        };
    }

    @Test(dataProvider = "samplesMaxValue")
    public void testOverflowValue(int maxValue) {
        panel = new NumberPanel(3, maxValue);

        panel.set(maxValue + 1);

        Assert.assertEquals(panel.get(), 0);
    }

}