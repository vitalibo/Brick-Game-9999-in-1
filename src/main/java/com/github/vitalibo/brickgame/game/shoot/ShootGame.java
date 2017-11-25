package com.github.vitalibo.brickgame.game.shoot;

import com.github.vitalibo.brickgame.core.Context;
import com.github.vitalibo.brickgame.game.Game;
import com.github.vitalibo.brickgame.game.Point;
import com.github.vitalibo.brickgame.util.CanvasTranslator;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public class ShootGame extends Game {

    @Getter
    private Army army;
    @Getter
    private Gun gun;
    @Getter
    private List<Point> shoots;

    public ShootGame(Context context) {
        super(context);
        setUp();
    }

    @Override
    public void init() {
        army = new Army();
        gun = new Gun();
        shoots = new CopyOnWriteArrayList<>();

        kernel.job(this, 1000 - speed.get() * 40, job -> doAttack());
        kernel.job("shoots", 25, job -> doShoot());
    }

    private void doShoot() {
        shoots.forEach(Point::doUp);
        if (army.killEnemy(shoots)) {
            score.inc(10);
        }

        repaint();
    }

    @Override
    public void doDown() {

    }

    @Override
    public void doLeft() {
        gun.doLeft();
        repaint();
    }

    @Override
    public void doRight() {
        gun.doRight();
        repaint();
    }

    @Override
    public void doUp() {
        doRotate();
    }

    @Override
    public void doRotate() {
        shoots.add(gun.fire());
        repaint();
    }

    private void setUp() {
        life.set(3);
        score.set(0);
    }

    private void doAttack() {
        army.makeAttack();
        repaint();

        if (army.hasWin()) {
            crash(gun.getPoint());
        }
    }

    private void repaint() {
        board.draw(CanvasTranslator.from(
            Stream.of(gun.getPoint()), shoots.stream(), army.stream()));
    }

}