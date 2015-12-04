package com.electdead.newgame.main;

import com.electdead.newgame.engine.AbstractGameLoop;
import com.electdead.newgame.engine.EngineV2;

import javax.swing.*;
import java.awt.*;

public class MainApp {
    //TODO background res (temporary)
    public static final int WIDTH = 1278;
    public static final int HEIGHT = 720;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame gameWindow = new GameWindow("Test game v0.2", WIDTH, HEIGHT);
                AbstractGameLoop engine = new EngineV2(WIDTH, HEIGHT);
                gameWindow.add(engine);
                gameWindow.setUndecorated(true);
                gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                gameWindow.setVisible(true);
                engine.start();
            }
        });
    }
}
