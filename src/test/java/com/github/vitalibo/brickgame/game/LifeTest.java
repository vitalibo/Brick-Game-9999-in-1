package com.github.vitalibo.brickgame.game;

import com.github.vitalibo.brickgame.core.Canvas;
import com.github.vitalibo.brickgame.util.CanvasTranslator;
import org.mockito.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LifeTest {

    @Mock
    private Canvas mockCanvas;
    @Captor
    private ArgumentCaptor<boolean[][]> captor;

    private Life life;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        life = Life.of(mockCanvas);
    }

    @BeforeMethod(dependsOnMethods = "setUp")
    public void reset() {
        life.set(-1);
        Mockito.reset(mockCanvas);
    }

    @DataProvider
    public Object[][] samples() {
        return new Object[][]{
            {0, expected(0x0, 0x0, 0x0, 0x0)},
            {1, expected(0x0, 0x0, 0x0, 0x8)},
            {3, expected(0x0, 0x8, 0x8, 0x8)},
            {7, expected(0x8, 0xC, 0xC, 0xC)},
            {16, expected(0xF, 0xF, 0xF, 0xF)}
        };
    }

    @Test(dataProvider = "samples")
    public void testRepaint(int value, boolean[][] expected) {
        life.set(value);

        Mockito.verify(mockCanvas).draw(captor.capture());
        Assert.assertEquals(captor.getValue(), expected);
    }

    @Test
    public void testNotRepaint() {
        life.set(5);
        Mockito.reset(mockCanvas);

        life.set(5);
        Mockito.verify(mockCanvas, Mockito.never()).draw(Mockito.any(boolean[][].class));
    }

    @Test
    public void cleanUp() {
        life.set(0);
        Mockito.reset(mockCanvas);

        life.set(0);
        Mockito.verify(mockCanvas).draw(Mockito.any(boolean[][].class));
    }

    private static boolean[][] expected(int... values) {
        return CanvasTranslator.from(4, values);
    }

}