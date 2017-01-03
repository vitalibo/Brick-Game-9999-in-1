package com.github.vitalibo.brickgame.core;

import com.github.vitalibo.brickgame.core.ui.BrickGameFrame;
import com.github.vitalibo.brickgame.game.Game;
import com.github.vitalibo.brickgame.game.Life;
import com.github.vitalibo.brickgame.game.Menu;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.experimental.Delegate;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;

public class Controller extends KeyAdapter implements Controllable {

    @Delegate(types = Controllable.class)
    private Game game;
    @Getter
    private boolean locked;

    private Context context;

    public Controller(BrickGameFrame frame, Kernel kernel) {
        frame.addKeyListener(this);
        this.context = new Context(
            frame.getBoard(), frame.getPreview(),
            frame.getScore(), frame.getSpeed(), frame.getLevel(),
            Life.of(frame.getPreview()),
            frame.getSound(), frame.getPause(),
            kernel, this);
    }

    @Synchronized
    @SneakyThrows(Exception.class)
    public void init(Class<? extends Game> cls) {
        Constructor<? extends Game> constructor = cls.getConstructor(Context.class);
        game = constructor.newInstance(context);

        game.init();
    }

    @Synchronized
    public void reinit() {
        game.init();
    }

    @Synchronized
    public void lock() {
        locked = true;
    }

    @Synchronized
    public void unlock() {
        locked = false;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_S:
                context.getSound().change();
                // TODO: on/off sound
                break;
            case KeyEvent.VK_P:
                boolean isPause = context.getPause().change();
                context.getKernel().values()
                    .forEach(job -> job.setPause(isPause));
                break;
            case KeyEvent.VK_R:
                context.getKernel().values()
                    .forEach(Kernel.Job::kill);
                // TODO: clean up
                init(Menu.class);
                context.getLife().set(0);
                this.unlock();
                break;
        }

        if (context.getPause().get() || isLocked()) {
            return;
        }

        switch (event.getKeyCode()) {
            case KeyEvent.VK_UP:
                this.doUp();
                break;
            case KeyEvent.VK_DOWN:
                this.doDown();
                break;
            case KeyEvent.VK_LEFT:
                this.doLeft();
                break;
            case KeyEvent.VK_RIGHT:
                this.doRight();
                break;
            case KeyEvent.VK_SPACE:
                this.doRotate();
                break;
        }
    }

}