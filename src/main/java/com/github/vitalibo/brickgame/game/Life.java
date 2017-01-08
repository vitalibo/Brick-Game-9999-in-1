package com.github.vitalibo.brickgame.game;

import com.github.vitalibo.brickgame.core.Canvas;
import com.github.vitalibo.brickgame.core.Number;
import com.github.vitalibo.brickgame.util.BooleanCollector;
import lombok.RequiredArgsConstructor;

import java.util.stream.IntStream;

@RequiredArgsConstructor(staticName = "of")
public class Life implements Number {

    private final Canvas preview;

    private int value;

    @Override
    public int get() {
        return value;
    }

    @Override
    public void set(int value) {
        if (this.value == value && value != 0) {
            return;
        }

        this.value = value;
        repaint();
    }

    private void repaint() {
        boolean[][] canvas = IntStream.rangeClosed(0, 3)
            .mapToObj(height -> IntStream.range(0, 4)
                .mapToObj(width -> (4 - height) + (4 * width) <= value)
                .collect(BooleanCollector.toArray()))
            .collect(BooleanCollector.toTwoDimensionalArray());

        preview.draw(canvas);
    }

}