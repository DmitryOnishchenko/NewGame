package com.electdead.newgame.gameobjectV2;

import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class GameObject<T> implements Comparable<T> {
    public boolean delete;

    public abstract void init();
    public abstract void updateInput(KeyEvent event);
    public abstract void updateAi();
    public abstract void updateAction();
    public abstract void updatePhysics();
    public abstract void updateGraphics();
    public abstract void render(Graphics2D graphics, double deltaTime);
    public abstract void switchState();
}
