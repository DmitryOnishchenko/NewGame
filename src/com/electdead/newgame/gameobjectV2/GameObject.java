package com.electdead.newgame.gameobjectV2;

import java.awt.*;
import java.awt.event.KeyEvent;

public interface GameObject {
    void init();
    void updateInput(KeyEvent event);
    void updateAi();
    void updateAction();
    void updatePhysics();
    void updateGraphics();
    void render(Graphics2D graphics, double deltaTime);
    void switchState();
}
