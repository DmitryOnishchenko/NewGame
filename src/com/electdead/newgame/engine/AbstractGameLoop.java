package com.electdead.newgame.engine;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public abstract class AbstractGameLoop extends JPanel implements Runnable {
    /* Main */
    protected int width;
    protected int height;
    protected int maxFps;
    protected int msPerFrame;

    /* Info */
    protected boolean showInfo = true;
    protected int tps = 0;
    protected int tpsInfo = 0;
    protected int fps = 0;
    protected int fpsInfo = 0;

    public AbstractGameLoop(int width, int height, int maxFps) {
        this.width = width;
        this.height = height;
        this.maxFps = maxFps;
        this.msPerFrame = 1000 / maxFps;

        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
    }

    public void start() {
        new Thread(this).start();
    }

    public abstract void run();
    public abstract void processInput();
    public abstract void update();
    public abstract void render(double deltaTime);
}
