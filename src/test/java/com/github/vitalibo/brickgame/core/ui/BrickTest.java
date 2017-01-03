package com.github.vitalibo.brickgame.core.ui;

import com.github.vitalibo.brickgame.core.ui.BrickPanel.Brick;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.*;

public class BrickTest {

    private Brick spyBrick;

    @BeforeMethod
    public void setUp() {
        spyBrick = Mockito.spy(Brick.class);
    }

    @Test
    public void testRepaint() {
        spyBrick.set(true);

        Mockito.verify(spyBrick).repaint();
    }

    @Test
    public void testNotRepaint() {
        spyBrick.set(true);
        Mockito.reset(spyBrick);

        spyBrick.set(true);
        Mockito.verify(spyBrick, Mockito.never()).repaint();
    }

    @DataProvider
    public Object[][] samples() {
        return new Object[][]{
            {false, new Color(0x61705B)}, {true, new Color(0x000000)}
        };
    }

    @Test(dataProvider = "samples")
    public void testPaintState(boolean state, Color color) {
        Graphics mockGraphics = Mockito.mock(Graphics.class);
        Mockito.when(mockGraphics.create()).thenReturn(mockGraphics);

        spyBrick.set(state);
        spyBrick.paintComponent(mockGraphics);

        Mockito.verify(mockGraphics, Mockito.times(2)).setColor(color);
    }

}