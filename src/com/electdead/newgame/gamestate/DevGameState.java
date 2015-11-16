package com.electdead.newgame.gamestate;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.engine.EngineV2;
import com.electdead.newgame.engine.GridOld;
import com.electdead.newgame.gameobject.GameObjectOld;
import com.electdead.newgame.gameobject.GameObjectType;
import com.electdead.newgame.gameobject.Side;
import com.electdead.newgame.gameobject.unit.UnitOld;
import com.electdead.newgame.gameobject.unit.ai.AIContainerOld;
import com.electdead.newgame.main.MainApp;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class DevGameState extends AbstractGameState {
    public static GridOld grid;

    public static ArrayList<GameObjectOld> renderObjects = new ArrayList<>(5000);

    private BufferedImage floorSprite0Level = (BufferedImage) Assets.getProperties("commonAssets").get("background0");
    private BufferedImage floorSprite1Level = (BufferedImage) Assets.getProperties("commonAssets").get("background1");
    private static BufferedImage floorImage = new BufferedImage(MainApp.WIDTH, MainApp.HEIGHT, BufferedImage.TYPE_INT_ARGB);
    public static Graphics2D floorGraphics = (Graphics2D) floorImage.getGraphics();

    public static ArrayList<BufferedImage> bloodSprites = new ArrayList<>();

    // TODO test variables
    private Random random = new Random();

    private int leftSpawnPoint = GridOld.INDENT_LEFT;
    private int rightSpawnPoint = MainApp.WIDTH + 50;

    private int testSpawnTimer;
    private int testSpawnTimer2;
    public static boolean SWARM = false;
    public static boolean DEBUG_MODE = false;
    public static boolean DEBUG_BOX = false;
    public static boolean DEBUG_TARGET = false;
    public static boolean DEBUG_GRID = false;
    public static boolean PAUSE = false;

    public DevGameState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
        /* Blood */
        HashMap<String, Object> commonAssets = Assets.getProperties("effectsAssets");
        bloodSprites = (ArrayList<BufferedImage>) commonAssets.get("bloodSprites");

        floorGraphics.drawImage(floorSprite0Level, 0, 0, null);

        grid = new GridOld();

        createDemoUnit("Human Soldier", 200, 400);
    }

    public GameObjectOld createDemoUnit(String name, float x, float y) {
        HashMap<String, Object> props = Assets.getProperties(name);

        Side side = (Side) props.get("side");
        UnitOld unit = new UnitOld(name, side, GameObjectType.UNIT, x, y);

        AIContainerOld aic = new AIContainerOld(unit);

//        GraphicsComponent gc = new UnitGraphicsComponent(object);

        unit.setAIContainer(aic);
//        object.setGraphicsComponent(gc);

//        grid.add(object);

//        GameObjectOld object = new GameObjectOld(name, side, GameObjectType.UNIT, x, y);
//        AIContainerOld container = new AIContainerOld((UnitOld) object);
//        GraphicsComponent gc = new UnitGraphicsComponent((UnitOld) object);
//
//        object.setAiContainer(container);
//        object.setGraphicsComponent(gc);

        return unit;
    }

    //TODO remake
    @Override
    public void processInput(KeyEvent event) {
        if (event != null && event.getID() == KeyEvent.KEY_PRESSED) {
            if (event.getKeyChar() == 'a') {
                createDemoUnit("Human Soldier", leftSpawnPoint, getRandomPointY());
            } else if (event.getKeyChar() == 's') {
                createDemoUnit("Human Archer", leftSpawnPoint, getRandomPointY());
            } else if (event.getKeyChar() == 'k') {
                createDemoUnit("Orc Soldier", rightSpawnPoint, getRandomPointY());
            } else if (event.getKeyChar() == 'i') {
                createDemoUnit("Orc Soldier Elite", rightSpawnPoint, getRandomPointY());
            } else if (event.getKeyChar() == 'l') {
                createDemoUnit("Orc Archer", rightSpawnPoint, getRandomPointY());
            } else if (event.getKeyChar() == 'h') {
                SWARM = !SWARM;
            } else if (event.getKeyCode() == 27) {
                System.exit(0);
            } else if (event.getKeyChar() == ' ') {
                PAUSE = !PAUSE;
            } else if (event.getKeyCode() == 10) {
                grid.clear();
                floorGraphics.drawImage(floorSprite0Level, 0, 0, null);
                SWARM = false;
            } else if (event.getKeyChar() == 'j') {
                DEBUG_MODE = !DEBUG_MODE;
                if (!DEBUG_MODE) {
                    DEBUG_BOX = false;
                    DEBUG_TARGET = false;
                    DEBUG_GRID = false;
                }
            } else if (DEBUG_MODE) {
                if (event.getKeyChar() == '1') {
                    DEBUG_BOX = !DEBUG_BOX;
                } else if (event.getKeyChar() == '2') {
                    DEBUG_TARGET = !DEBUG_TARGET;
                } else if (event.getKeyChar() == '3') {
                    DEBUG_GRID = !DEBUG_GRID;
                }
            }
        }
    }

    // old
    public void update2() {
        if (PAUSE) {
            return;
        }

        grid.update();
        grid.checkDelete();

        if (SWARM && ++testSpawnTimer > 2) {
            SWARM();
            testSpawnTimer = 0;
        }
    }

    // multithreaded
    public void update() {
        if (PAUSE) {
            return;
        }

        int needToProcess = grid.size();
        if (needToProcess != 0) {
            EngineV2.startProcess(needToProcess);
        }

        if (SWARM && ++testSpawnTimer > 2) {
            SWARM();
            testSpawnTimer = 0;
        }
    }

    @Override
    public void render(Graphics2D g2, double deltaTime) {
        g2.drawImage(floorImage, 0, 0, null);
        if (DEBUG_GRID) {
            grid.render(g2, deltaTime);
        }

        renderObjects.clear();
//        renderObjects.addAll(grid.getAllObjects());
        Collections.sort(renderObjects);

        for (GameObjectOld obj : renderObjects) {
            obj.render(g2, deltaTime);
        }

        g2.drawImage(floorSprite1Level, 0, 0, null);

        g2.setPaint(Color.WHITE);
        g2.drawString("GameObjects: " + grid.getAllObjects().size() + " | Units: " + grid.size(), 5, 36);

        g2.drawString("To spawn Human Soldier press \"A\"", 100, 72);
        g2.drawString("To spawn Human Archer press \"S\"", 100, 90);

        g2.drawString("Exit - \"Esc\"", 480, 72);
        if (!SWARM) {
            g2.drawString("To start Demo Mode press \"H\"", 580, 90);
        } else {
            g2.setPaint(Color.RED);
            g2.drawString("To stop Demo Mode press \"H\"", 580, 90);
        }
        if (!PAUSE) {
            g2.setPaint(Color.WHITE);
            g2.drawString("PAUSE - \"Space\"", 580, 108);
        } else {
            g2.setPaint(Color.RED);
            g2.drawString("UNPAUSE - \"Space\"", 580, 108);
        }
        g2.setPaint(Color.WHITE);
        g2.drawString("New Game - \"Enter\"", 750, 108);

        g2.drawString("To spawn Orc Soldier press \"K\"", 1000, 72);
        g2.drawString("To spawn Orc Soldier Elite press \"I\"", 1000, 90);
        g2.drawString("To spawn Orc Archer press \"L\"", 1000, 108);

        /* Debug menu */
        if (DEBUG_MODE) {
            drawDebugMenu(g2);
            g2.setPaint(Color.RED);
            g2.drawString("To deactivate Debug mode press \"J\" again", 580, 72);
        } else {
            g2.drawString("To activate Debug mode press \"J\" (for debug only!)", 580, 72);
        }
    }

    private void drawDebugMenu(Graphics2D g2) {
        g2.setPaint(Color.RED);
        int rectY = 16;
        int stringY = 30;
        g2.drawRect(290, 6, 610, 44);

        g2.setPaint(Color.WHITE);
        g2.drawString("To activate box model: \"1\"", 300, stringY);
        if (DEBUG_BOX) {
            g2.drawRect(296, rectY, 200, 20);
        }

        g2.setPaint(Color.YELLOW);
        g2.drawString("To activate target tracking: \"2\"", 500, stringY);
        if (DEBUG_TARGET) {
            g2.drawRect(496, rectY, 200, 20);
        }

        g2.setPaint(Color.GREEN);
        g2.drawString("To activate grid: \"3\"", 700, stringY);
        if (DEBUG_GRID) {
            g2.drawRect(696, rectY, 200, 20);
        }
    }

    /* Benchmark-Demo test */
    public void SWARM() {
        createDemoUnit("Human Soldier", leftSpawnPoint, getRandomPointY());
        createDemoUnit("Orc Soldier", rightSpawnPoint, getRandomPointY());

        if (++testSpawnTimer2 >= 8) {
            createDemoUnit("Orc Soldier", rightSpawnPoint, getRandomPointY());
        }

        if (++testSpawnTimer2 >= 10) {
            testSpawnTimer2 = 0;
            createDemoUnit("Orc Soldier", rightSpawnPoint, getRandomPointY());
            createDemoUnit("Orc Archer", rightSpawnPoint, getRandomPointY());
            createDemoUnit("Human Archer", leftSpawnPoint, getRandomPointY());
            createDemoUnit("Human Archer", leftSpawnPoint, getRandomPointY());
        }
    }

    private int getRandomPointY() {
        return (int) (random.nextFloat() * 500 + GridOld.INDENT_TOP);
    }
}
