package com.github.vitalibo.brickgame.core;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class NumberTest {

    private Number number;

    @BeforeMethod
    public void setUp() {
        number = new EmbeddedNumber();
    }

    @DataProvider
    public Object[][] samples() {
        return new Object[][]{
            {0, 1}, {100, 101}, {12345, 12346}
        };
    }

    @Test(dataProvider = "samples")
    public void testInc(int value, int expected) {
        number.set(value);

        number.inc();

        Assert.assertEquals(number.get(), expected);
    }

    @Test(dataProvider = "samples")
    public void testDec(int expected, int value) {
        number.set(value);

        number.dec();

        Assert.assertEquals(number.get(), expected);
    }

    @DataProvider
    public Object[][] samplesWithStep() {
        return new Object[][]{
            {5, 1, 6}, {100, 12345, 12445}, {12345, 12345, 24690}, {54321, 12345, 66666}
        };
    }

    @Test(dataProvider = "samplesWithStep")
    public void testIncStep(int step, int value, int expected) {
        number.set(value);

        number.inc(step);

        Assert.assertEquals(number.get(), expected);
    }

    @Test(dataProvider = "samplesWithStep")
    public void testDecStep(int step, int expected, int value) {
        number.set(value);

        number.dec(step);

        Assert.assertEquals(number.get(), expected);
    }

    private class EmbeddedNumber implements Number {

        private int value;

        @Override
        public int get() {
            return value;
        }

        @Override
        public void set(int value) {
            this.value = value;
        }

    }

}