package com.electdead.newgame.engine;

import com.electdead.newgame.gameobjectV2.GameObject;
import com.electdead.newgame.gamestate.GameStateManager;
import com.electdead.newgame.input.EngineInputHandler;

import java.awt.*;
import java.awt.image.VolatileImage;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("serial")
public class EngineV2 extends AbstractGameLoop {
    /* Update parameters */
    public static final int UPDATES_PER_SEC = 100;
    public static final int MS_PER_UPDATE = 1000 / UPDATES_PER_SEC;

    /* Render parameters */
    public static final int MAX_FPS = 120;
    public static final int MS_PER_FRAME = 1000 / MAX_FPS;
    public static final boolean USE_FPS_LIMIT = true;

    /* Graphics */
    private VolatileImage currentFrame;
    private Graphics2D currentG2D;

    /* Managers */
    private GameStateManager gsm;
    private static EngineInputHandler inputHandler;

    /* Game objects */
    public static List<GameObject> gameObjects;
    public static List<GameObject> renderObjects;

    /* Threads */
    private UpdateThread updateThread;
    private RenderThread renderThread;
    private InputHandlerThread inputHandlerThread;
    private static ExecutorService service = Executors.newFixedThreadPool(32);

    /* Stats */
    private boolean SHOW_INFO = true;
    private UpdateStats updateStats = new UpdateStats();
    private RenderStats renderStats = new RenderStats();

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
        currentFrame = initFrame();
//        currentFrame.setAccelerationPriority(1);
        currentG2D = getGraphics(currentFrame);

        /* InputHandlerThread */
        inputHandlerThread = new InputHandlerThread(this);

        /* Updater */
        updateThread = new UpdateThread(this, updateStats);

        /* Render */
        renderThread = new RenderThread(this, renderStats);
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
        gsm.processInput(inputHandler.getKeyEvent());
        inputHandler.clear();
    }

    @Override
    public void update() {
        gsm.update();
    }

    @Override
    public void render(double deltaTime) {
		/* Clear frame */
        currentG2D.clearRect(0, 0, width, height);

		/* Render state */
        gsm.render(currentG2D, deltaTime);

		/* FPS/TPS info */
        if (SHOW_INFO) {
            currentG2D.setPaint(Color.WHITE);
            currentG2D.drawString(renderStats.report() + "   " + updateStats.report(), 5, 18);
        }

		/* Draw frame */
        Graphics graphics = getGraphics();
        graphics.drawImage(currentFrame, 0, 0, null);
    }

    @Override
    public void run() {
        inputHandlerThread.start();
        updateThread.start();
        renderThread.start();
    }

    public static long getTime() {
        return System.currentTimeMillis();
    }

    public static void submitTask(Runnable task) {
        service.submit(task);
    }
}
