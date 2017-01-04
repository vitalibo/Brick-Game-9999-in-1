package com.github.vitalibo.brickgame;


import com.github.vitalibo.brickgame.core.Controller;
import com.github.vitalibo.brickgame.core.Kernel;
import com.github.vitalibo.brickgame.core.ui.BrickGameFrame;
import com.github.vitalibo.brickgame.game.Game;
import com.github.vitalibo.brickgame.game.Menu;
import com.github.vitalibo.brickgame.game.snake.SnakeGame;

public class Run {

    static {
        @SuppressWarnings("unchecked")
        Class<? extends Game>[] GAMES = new Class[]{
            SnakeGame.class
        };
        Menu.setGames(GAMES);
    }

    public static void main(String[] args) {
        BrickGameFrame frame = new BrickGameFrame();
        Controller controller = new Controller(frame, new Kernel());
        controller.init(Menu.class);
    }

}