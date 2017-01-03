package com.github.vitalibo.brickgame.core;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class StateTest {

    private State state;

    @BeforeMethod
    public void setUp() {
        state = new EmbeddedState();
    }

    @DataProvider
    public Object[][] samples() {
        return new Object[][]{
            {true}, {false}
        };
    }

    @Test(dataProvider = "samples")
    public void testChangeState(boolean value) {
        state.set(value);

        state.change();

        Assert.assertEquals(state.get(), !value);
    }

    private class EmbeddedState implements State {

        private boolean state;

        @Override
        public boolean get() {
            return state;
        }

        @Override
        public void set(boolean state) {
            this.state = state;
        }

    }

}