package com.github.vitalibo.brickgame.core.ui;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class IconPanelTest {

    private IconPanel spyPanel;

    @BeforeMethod
    public void setUp() {
        spyPanel = Mockito.spy(new IconPanel("on", "off"));
    }

    @DataProvider
    public Object[][] samples() {
        return new Object[][]{
            {true}, {false}
        };
    }

    @Test(dataProvider = "samples")
    public void testRepaint(boolean state) {
        spyPanel.set(!state);
        Mockito.reset(spyPanel);

        spyPanel.set(state);

        Mockito.verify(spyPanel).repaint();
    }

    @Test(dataProvider = "samples")
    public void testNotRepaint(boolean state) {
        spyPanel.set(state);
        Mockito.reset(spyPanel);

        spyPanel.set(state);

        Mockito.verify(spyPanel, Mockito.never()).repaint();
    }

}