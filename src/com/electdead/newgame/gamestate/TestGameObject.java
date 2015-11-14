package com.electdead.newgame.gamestate;

import com.electdead.newgame.gameobject.GameObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class TestGameObject implements GameObject {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private int speed;

    public TestGameObject() {
        Random r = new Random();
        x = r.nextInt() % 1200 + 20;
        y = r.nextInt() % 500 + 20;
        speed = r.nextInt() % 10 + 2;
        width = r.nextInt() % 200 + 50;
        height = r.nextInt() % 200 + 50;
        color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255), r.nextInt(255));
    }

    @Override
    public void updateInput(KeyEvent event) {

    }

    @Override
    public void updateAi() {

    }

    @Override
    public void updateAction() {
        x += speed;
        if (x + width >= 1280 || x < 0) {
            speed = -speed;
        }
    }

    @Override
    public void updatePhysics() {

    }

    @Override
    public void updateGraphics() {

    }

    @Override
    public void render(Graphics2D graphics, double deltaTime) {
        graphics.setPaint(color);
        graphics.fillRect(x, y, width, height);
    }
}
