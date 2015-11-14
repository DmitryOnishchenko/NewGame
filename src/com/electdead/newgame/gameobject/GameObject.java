package com.electdead.newgame.gameobject;

import java.awt.*;
import java.awt.event.KeyEvent;

public interface GameObject {
    void updateInput(KeyEvent event);
    void updateAi();
    void updateAction();
    void updatePhysics();
    void updateGraphics();
    void render(Graphics2D graphics, double deltaTime);
}
