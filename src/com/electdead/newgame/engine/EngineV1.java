package com.electdead.newgame.engine;

import com.electdead.newgame.gamestate.GameStateManager;
import com.electdead.newgame.input.EngineInputHandler;

import java.awt.*;
import java.awt.image.VolatileImage;

@SuppressWarnings("serial")
public class EngineV1 extends AbstractGameLoop {
    /* Graphics */
    public static final int MAX_FPS = 120;
    public static final int UPDATES_PER_SEC = 50;
    public static final int MS_PER_UPDATE = 1000 / UPDATES_PER_SEC;
    public static final boolean useFpsLimit = true;
    private VolatileImage currentFrame;
    private Graphics2D currentG2D;

    /* Managers */
    private GameStateManager gsm;
    private static EngineInputHandler inputHandler;

    public EngineV1(int width, int height) {
        super(width, height, MAX_FPS);
        init();
    }

    public void init() {
        inputHandler = new EngineInputHandler();
        addKeyListener(inputHandler);

        gsm = new GameStateManager();
        gsm.init();

        setDoubleBuffered(true);
        currentFrame = initFrame();
        currentFrame.setAccelerationPriority(1);
        currentG2D = getGraphics(currentFrame);
    }

    private VolatileImage initFrame() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
        VolatileImage frame = gc.createCompatibleVolatileImage(width, height);
        frame.validate(gc);

        return frame;
    }

    private Graphics2D getGraphics(VolatileImage frame) {
        Graphics2D g2 = (Graphics2D) frame.getGraphics();
		/* Test for text */
//		g2Next.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		/* Test for shapes */
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        return g2;
    }

    public static EngineInputHandler getInputHandler() {
        return inputHandler;
    }

    @Override
    public void processInput() {
        gsm.processInput(inputHandler.getKeyEvent());
        inputHandler.clear();
    }

    @Override
    public void update() {
        gsm.update();
        tps++;
    }

    @Override
    public void render(double deltaTime) {
		/* Clear frame */
        currentG2D.clearRect(0, 0, width, height);

		/* Render state */
        gsm.render(currentG2D, deltaTime);

		/* FPS/TPS info */
        if (showInfo) {
            fps++;
            currentG2D.setPaint(Color.WHITE);
            currentG2D.drawString("FPS: " + fpsInfo + " | TPS: " + tpsInfo, 5, 18);
        }

		/* Draw frame */
        Graphics graphics = getGraphics();
        graphics.drawImage(currentFrame, 0, 0, null);
        graphics.dispose();
    }

    @Override
    public void run() {
        long timer = getCurrentTime();
        long previous = getCurrentTime();
        long lag = 0;

        while (true) {
            long current = getCurrentTime();
            long elapsed = current - previous;
            previous = current;
            lag += elapsed;

            processInput();

            while (lag >= MS_PER_UPDATE) {
                update();
                lag -= MS_PER_UPDATE;
            }

            double deltaTime = 1 - lag / MS_PER_UPDATE;
            render(deltaTime);

            if (getCurrentTime() - timer >= 1000) {
                tpsInfo = tps;
                tps = 0;
                fpsInfo = fps;
                fps = 0;
                timer += 1000;
            }

            if (useFpsLimit) {
                long sleepFor = current + msPerFrame - getCurrentTime();
                try {
                    if (sleepFor > 0) Thread.sleep(sleepFor);
                } catch (InterruptedException ex) { ex.printStackTrace(); }
            }
        }
    }

    private long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
