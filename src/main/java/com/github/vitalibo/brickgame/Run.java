package com.github.vitalibo.brickgame;


import com.github.vitalibo.brickgame.core.Controller;
import com.github.vitalibo.brickgame.core.Kernel;
import com.github.vitalibo.brickgame.core.ui.BrickGameFrame;
import com.github.vitalibo.brickgame.game.Game;
import com.github.vitalibo.brickgame.game.Menu;
import com.github.vitalibo.brickgame.game.race.RaceGame;
import com.github.vitalibo.brickgame.game.shoot.ShootGame;
import com.github.vitalibo.brickgame.game.snake.SnakeGame;
import com.github.vitalibo.brickgame.game.tanks.TanksGame;
import com.github.vitalibo.brickgame.game.tetris.TetrisGame;

public class Run {

    static {
        @SuppressWarnings("unchecked")
        Class<? extends Game>[] GAMES = new Class[]{
            SnakeGame.class,
            RaceGame.class,
            TetrisGame.class,
            ShootGame.class,
            TanksGame.class
        };
        Menu.setGames(GAMES);
    }

    public static void main(String[] args) {
        BrickGameFrame frame = new BrickGameFrame();
        Controller controller = new Controller(frame, new Kernel());
        controller.init(Menu.class);
    }

}