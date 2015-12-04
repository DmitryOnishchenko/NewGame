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
    public static final int UPDATES_PER_SEC = 50;
    public static final int MS_PER_UPDATE = 1000 / UPDATES_PER_SEC;

    /* Render parameters */
    public static final int MAX_FPS = 120;
    public static final int MS_PER_FRAME = 1000 / MAX_FPS;
    public static final boolean USE_FPS_LIMIT = true;

    /* Graphics */
    private VolatileImage currentFrame;
    private Graphics2D currentG2D;

    //TODO new graphics
    private VolatileImage volatileImg = createVolatileImage(width, height, Transparency.OPAQUE);
//    private VolatileImage volatileImg;

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
    private static ExecutorService service = Executors.newFixedThreadPool(1);

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
//        currentFrame = initFrame();
//        currentG2D = getGraphics(currentFrame);

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

//    private Graphics2D getGraphics(VolatileImage frame) {
//        Graphics2D g2 = frame.createGraphics();
//        //TODO Test rendering hints
		/* Test for text */
//		g2Next.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		/* Test for shapes */
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//        return g2;
//    }

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

//    @Override
    public void render(double deltaTime) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();

        Graphics2D g = null;

        do {

            int valid = volatileImg.validate(gc);

            if (valid == VolatileImage.IMAGE_INCOMPATIBLE) {
                volatileImg = createVolatileImage(width, height, Transparency.OPAQUE);
            }

            try {
                g = volatileImg.createGraphics();

                gsm.render(g, deltaTime);

                /* FPS/TPS info */
                if (SHOW_INFO) {
                    g.setPaint(Color.WHITE);
                    g.drawString(renderStats.report() + "   " + updateStats.report(), 5, 18);
                }
//                mySprite.draw(g); // This is assumed to be created somewhere else, and is only used as an example.
            } finally {
                // It's always best to dispose of your Graphics objects.
                g.dispose();
            }
            getGraphics().drawImage(volatileImg, 0, 0, this);
        } while (volatileImg.contentsLost());

    }

    private VolatileImage createVolatileImage(int width, int height, int transparency) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
        VolatileImage image = null;

        image = gc.createCompatibleVolatileImage(width, height, transparency);

        int valid = image.validate(gc);

        if (valid == VolatileImage.IMAGE_INCOMPATIBLE) {
            image = this.createVolatileImage(width, height, transparency);
            return image;
        }

        return image;
    }

//    @Override
    public void render2(double deltaTime) {
        // create the hardware accelerated image.
        if (volatileImg == null) {
            createBackBuffer();
        }

        // Main rendering loop. Volatile images may lose their contents.
        // This loop will continually render to (and produce if neccessary) volatile images
        // until the rendering was completed successfully.
        do {
            // Validate the volatile image for the graphics configuration of this
            // component. If the volatile image doesn't apply for this graphics configuration
            // (in other words, the hardware acceleration doesn't apply for the new device)
            // then we need to re-create it.
            GraphicsConfiguration gc = this.getGraphicsConfiguration();
            int valCode = volatileImg.validate(gc);

            // This means the device doesn't match up to this hardware accelerated image.
            if(valCode==VolatileImage.IMAGE_INCOMPATIBLE){
                createBackBuffer(); // recreate the hardware accelerated image.
            }

            //TODO test createGraphics()
            Graphics2D offscreenGraphics = (Graphics2D) volatileImg.getGraphics();

            // rendering logic
            gsm.render(offscreenGraphics, deltaTime);

            /* FPS/TPS info */
            if (SHOW_INFO) {
                offscreenGraphics.setPaint(Color.WHITE);
                offscreenGraphics.drawString(renderStats.report() + "   " + updateStats.report(), 5, 18);
            }

            // paint back buffer to main graphics
            getGraphics().drawImage(volatileImg, 0, 0, this);
            // Test if content is lost
            offscreenGraphics.dispose();
        } while (volatileImg.contentsLost());


//		/* Clear frame */
//        currentG2D.clearRect(0, 0, width, height);
//
//		/* Render state */
//        gsm.render(currentG2D, deltaTime);
//
//		/* FPS/TPS info */
//        if (SHOW_INFO) {
//            currentG2D.setPaint(Color.WHITE);
//            currentG2D.drawString(renderStats.report() + "   " + updateStats.report(), 5, 18);
//        }
//
//		/* Draw frame */
//        Graphics graphics = getGraphics();
//        graphics.drawImage(currentFrame, 0, 0, null);
    }

    /**
     * This method produces a new volatile image.
     */
    private void createBackBuffer() {
        GraphicsConfiguration gc = getGraphicsConfiguration();
        volatileImg = gc.createCompatibleVolatileImage(getWidth(), getHeight());
//        volatileImg.setAccelerationPriority(1);
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
