package com.github.vitalibo.brickgame.core;

import com.github.vitalibo.brickgame.util.Builder;

public interface State {

    default boolean change() {
        return Builder.of(get())
            .map(s -> !s)
            .with(this::set)
            .get();
    }

    boolean get();

    void set(boolean state);

}