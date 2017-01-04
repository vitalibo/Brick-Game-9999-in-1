package com.github.vitalibo.brickgame.game.snake;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.github.vitalibo.brickgame.game.snake.Direction.*;

public class DirectionTest {

    @DataProvider
    public Object[][] samples() {
        return new Object[][]{
            {DOWN, UP}, {UP, DOWN}, {LEFT, RIGHT}, {RIGHT, LEFT}, {NOT_DEFINED, NOT_DEFINED}
        };
    }

    @Test(dataProvider = "samples")
    public void testReverse(Direction direction, Direction expected) {
        Direction reverse = direction.reverse();

        Assert.assertEquals(reverse, expected);
    }

}