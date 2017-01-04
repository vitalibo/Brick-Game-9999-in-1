package com.github.vitalibo.brickgame.game.race;

import com.github.vitalibo.brickgame.core.Context;
import com.github.vitalibo.brickgame.core.GameException;
import com.github.vitalibo.brickgame.game.Game;
import com.github.vitalibo.brickgame.util.CanvasTranslator;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class RaceGame extends Game {

    @Getter
    private Car car;
    @Getter
    private List<Car> cars;
    @Getter
    private Road road;

    public RaceGame(Context context) {
        super(context);
        setUp();
    }

    private void setUp() {
        score.set(0);
        life.set(3);
    }

    @Override
    public void init() {
        car = Car.init();
        cars = new ArrayList<>();
        cars.add(Car.generate());
        road = Road.init();

        kernel.job(this, 300 - speed.get() * 18, job -> onMove());
    }

    private void onMove() {
        cars.forEach(Car::doDown);
        road.doDown();

        if (road.getTraffic() % 9 == 0) {
            cars.add(Car.generate());

            if (cars.size() > 3) {
                cars.remove(0);
            }
        }

        score.inc(10);
        repaint();

        try {
            car.verifyCrash(cars);
        } catch (GameException e) {
            crash(e.getPoint());
        }

        verifyLevelUp();
    }

    @Override
    public void doDown() {
    }

    @Override
    public void doLeft() {
        car.doLeft();

        repaint();
        try {
            car.verifyCrash(cars);
        } catch (GameException e) {
            crash(e.getPoint());
        }
    }

    @Override
    public void doRight() {
        car.doRight();

        repaint();
        try {
            car.verifyCrash(cars);
        } catch (GameException e) {
            crash(e.getPoint());
        }
    }

    @Override
    public void doUp() {
        onMove();
    }

    @Override
    public void doRotate() {
        onMove();
    }

    private void verifyLevelUp() {
        if (road.getTraffic() < 1000) {
            return;
        }

        kernel.forEach((s, job) -> job.kill());
        speed.inc();
        init();
    }

    private void repaint() {
        boolean[][] from = CanvasTranslator.from(
            car.stream(), cars.stream().flatMap(Car::stream), road.stream());

        board.draw(from);
    }

}