package com.electdead.newgame.engine;

import com.electdead.newgame.engine.thread.*;
import com.electdead.newgame.gameobjectV2.GameObject;
import com.electdead.newgame.gamestate.GameStateManager;
import com.electdead.newgame.input.EngineInputHandler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.VolatileImage;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("serial")
public class EngineV2 extends AbstractGameLoop {
    /* Graphics */
    public static final int MAX_FPS = 120;
    public static final int UPDATES_PER_SEC = 50;
    public static final int MS_PER_UPDATE = 1000 / UPDATES_PER_SEC;
    public static final boolean useFpsLimit = true;
    private VolatileImage frame;
    private Graphics2D frameGraphics;

    /* Managers */
    private GameStateManager gsm;
    private static EngineInputHandler inputHandler;

    /* Threads */
    private static Updater inputUpdater;
    private static Updater aiUpdater;
    private static Updater actionUpdater;
    private static Updater physicsUpdater;
    private static Updater graphicsUpdater;

    /* GameObjects */
    public static volatile List<GameObject> gameObjects = Collections.emptyList();

    public EngineV2(int width, int height) {
        super(width, height, MAX_FPS);
        init();
    }

    public void init() {
        inputHandler = new EngineInputHandler();
        addKeyListener(inputHandler);

        gsm = new GameStateManager();
        gsm.init();

        setDoubleBuffered(true);
        frame = initFrame();
        frame.setAccelerationPriority(1);
        frameGraphics = getGraphics(frame);

        /* UpdateMethods init */
        UpdateMethod inputUpdateMethod      = new InputUpdateMethod();
        UpdateMethod aiUpdateMethod         = new AiUpdateMethod();
        UpdateMethod actionUpdateMethod     = new ActionUpdateMethod();
        UpdateMethod physicsUpdateMethod    = new PhysicsUpdateMethod();
        UpdateMethod graphicsUpdateMethod   = new GraphicsUpdateMethod();

        /* Threads init */
        inputUpdater    = new Updater("InputUpdater", inputUpdateMethod, true);
        aiUpdater       = new Updater("AiUpdater", aiUpdateMethod, false);
        actionUpdater   = new Updater("ActionUpdater", actionUpdateMethod, false);
        physicsUpdater  = new Updater("PhysicsUpdater", physicsUpdateMethod, false);
        graphicsUpdater = new Updater("GraphicsUpdater", graphicsUpdateMethod, false);

        /* Updaters queue */
        inputUpdater.setNextUpdater(aiUpdater);
        aiUpdater.setNextUpdater(actionUpdater);
        actionUpdater.setNextUpdater(physicsUpdater);
        physicsUpdater.setNextUpdater(graphicsUpdater);
        /* Last to first */
//        graphicsUpdater.setNextUpdater(aiUpdater);

        /* Start threads */
        inputUpdater.start();
        aiUpdater.start();
        actionUpdater.start();
        physicsUpdater.start();
        graphicsUpdater.start();
    }

    private VolatileImage initFrame() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
        VolatileImage volatileImage = gc.createCompatibleVolatileImage(width, height);
        volatileImage.validate(gc);

        return volatileImage;
    }

    private Graphics2D getGraphics(VolatileImage volatileImage) {
        Graphics2D g2 = (Graphics2D) volatileImage.getGraphics();
        //TODO Test rendering hints
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
        KeyEvent keyEvent = inputHandler.getKeyEvent();
        if (keyEvent != null) {
            gsm.processInput(keyEvent);
        }
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
//        frameGraphics.clearRect(0, 0, width, height);
        frameGraphics.setPaint(Color.BLACK);
        frameGraphics.fillRect(0, 0, width, height);

		/* Render state */
        gsm.render(frameGraphics, deltaTime);

		/* FPS/TPS info */
        if (showInfo) {
            fps++;
            frameGraphics.setPaint(Color.WHITE);
            frameGraphics.drawString("FPS: " + fpsInfo + " | TPS: " + tpsInfo, 5, 18);
        }

		/* Draw frame */
        Graphics graphics = getGraphics();
        graphics.drawImage(frame, 0, 0, null);
        graphics.dispose();
    }

    @Override
    public void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long timer = getTime();
                long previous = getTime();
                long lag = 0;

                while (true) {
                    long current = getTime();
                    long elapsed = current - previous;
                    previous = current;
                    lag += elapsed;

                    processInput();

                    while (lag >= MS_PER_UPDATE) {
                        update();
                        //TODO wait for last updater
                        while (gameObjects.size() != 0 && !graphicsUpdater.isDone()) {
                            Thread.yield();
                            try {
                                // wait
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        lag -= MS_PER_UPDATE;
                    }

                    double deltaTime = 1 - lag / MS_PER_UPDATE;
                    render(deltaTime);

                    if (getTime() - timer >= 1000) {
                        tpsInfo = tps;
                        tps = 0;
                        fpsInfo = fps;
                        fps = 0;
                        timer += 1000;
                    }

                    if (useFpsLimit) {
                        long sleepFor = current + msPerFrame - getTime();
//                        System.out.println("Sleep for:\t " + sleepFor + " ms");
                        try {
                            if (sleepFor > 0) {
                                Thread.sleep(sleepFor);
                            }
                        } catch (InterruptedException ex) { ex.printStackTrace(); }
                    }
                }
            }
        }).start();
    }

    private long getTime() {
        return System.currentTimeMillis();
    }

    public static void startProcess(int needToProcess) {
        inputUpdater.startProcess(needToProcess);
    }

    public static void loopEnded() {
        inputUpdater.setDone(false);
    }

    public static void inputHandled() {
        inputHandler.clear();
    }
}
