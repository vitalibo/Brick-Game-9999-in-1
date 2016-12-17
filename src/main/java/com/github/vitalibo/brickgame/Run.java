package com.github.vitalibo.brickgame;


import com.github.vitalibo.brickgame.core.ui.BrickGameFrame;

public class Run {

    public static void main(String[] args) throws InterruptedException {
        BrickGameFrame frame = new BrickGameFrame();
        boolean[][] canvas = new boolean[20][10];
        int x = 0, y = -1;

        while (true) {
            if (++y >= 20) {
                y = 0;
                if (++x >= 10) {
                    x = 0;
                }
            }

            canvas[y][x] = true;
            frame.getFrame().draw(canvas);
            canvas[y][x] = false;
            frame.getScore().inc();

            Thread.sleep(50);
        }
    }

}