package com.github.vitalibo.brickgame.game;

import com.github.vitalibo.brickgame.core.*;
import com.github.vitalibo.brickgame.core.Number;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Game implements Controllable {

    protected final Context context;
    protected final Canvas board;
    protected final Canvas preview;
    protected final Number score;
    protected final Number speed;
    protected final Number level;
    protected final Number life;
    protected final State sound;
    protected final State pause;
    protected final Kernel kernel;
    protected final Controller controller;

    public Game(Context context) {
        this(context, context.getBoard(), context.getPreview(),
            context.getScore(), context.getSpeed(), context.getLevel(), context.getLife(),
            context.getSound(), context.getPause(),
            context.getKernel(), context.getController());
    }

    public abstract void init();

    public void crash(Point point) {
        life.dec();

        Crash.on(context, point);
    }

}