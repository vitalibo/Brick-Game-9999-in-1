package com.github.vitalibo.brickgame.game.tetris.tetromino;

import com.github.vitalibo.brickgame.game.tetris.Tetromino;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class FactoryTest {

    private static final Random random = new Random();

    @DataProvider
    public Object[][] suppliers() {
        return new Object[][]{
            {supplier(Factory::generateI), I.class}, {supplier(Factory::generateJ), J.class},
            {supplier(Factory::generateL), L.class}, {supplier(Factory::generateO), O.class},
            {supplier(Factory::generateS), S.class}, {supplier(Factory::generateT), T.class},
            {supplier(Factory::generateZ), Z.class}
        };
    }

    @Test(dataProvider = "suppliers")
    public void testGenerate(Supplier<Tetromino> supplier, Class<? extends Tetromino> cls) {
        Tetromino tetromino = supplier.get();

        Assert.assertEquals(tetromino.getClass(), cls);
        Assert.assertTrue(tetromino.getState() < 4);
    }

    @DataProvider
    public Object[][] functions() {
        return new Object[][]{
            {function(Factory::createI), I.class}, {function(Factory::createJ), J.class},
            {function(Factory::createL), L.class}, {function(Factory::createO), O.class},
            {function(Factory::createS), S.class}, {function(Factory::createT), T.class},
            {function(Factory::createZ), Z.class},
        };
    }

    @Test(dataProvider = "functions")
    public void testCreate(Function<Integer, Tetromino> function, Class<? extends Tetromino> cls) {
        int state = random.nextInt(4);

        Tetromino tetromino = function.apply(state);

        Assert.assertEquals(tetromino.getClass(), cls);
        Assert.assertEquals(tetromino.getState(), state);
        Assert.assertEquals(tetromino.getStates().length, 4);
    }

    private static Supplier<Tetromino> supplier(Supplier<Tetromino> supplier) {
        return supplier;
    }

    private static Function<Integer, Tetromino> function(Function<Integer, Tetromino> function) {
        return function;
    }

}