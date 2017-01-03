package com.github.vitalibo.brickgame.core.ui;

import com.github.vitalibo.brickgame.core.State;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class IconPanel extends JPanel implements State {

    private boolean state;

    IconPanel(String on, String off) {
        this(on, off, false);
    }

    IconPanel(String on, String off, boolean state) {
        this.state = state;
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));
        this.setOpaque(false);
        this.add(new Canvas(IconPanel.resourceAsImage(on), IconPanel.resourceAsImage(off)));
    }

    @Override
    public boolean get() {
        return state;
    }

    @Override
    public synchronized void set(boolean state) {
        if (this.state == state) {
            return;
        }

        this.state = state;
        repaint();
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        this.getComponent(0).setPreferredSize(preferredSize);
    }

    @SneakyThrows(IOException.class)
    private static Image resourceAsImage(String resource) {
        return ImageIO.read(ClassLoader.getSystemResourceAsStream(String.format("ui/%s.bmp", resource)));
    }

    private class Canvas extends JPanel {

        private final Image ON;
        private final Image OFF;

        private Canvas(Image ON, Image OFF) {
            this.ON = ON;
            this.OFF = OFF;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(state ? ON : OFF, 0, 0, null);
        }

    }

}