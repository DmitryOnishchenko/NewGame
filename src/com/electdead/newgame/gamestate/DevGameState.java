package com.electdead.newgame.gamestate;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.engine.Grid;
import com.electdead.newgame.gameobjects.GameObject;
import com.electdead.newgame.gameobjects.GameObjectType;
import com.electdead.newgame.gameobjects.Side;
import com.electdead.newgame.gameobjects.units.Unit;
import com.electdead.newgame.gameobjects.units.ai.AIContainer;
import com.electdead.newgame.graphics.GraphicsComponent;
import com.electdead.newgame.graphics.UnitGraphicsComponent;
import com.electdead.newgame.main.MainApp;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class DevGameState extends AbstractGameState {
    public static Grid grid;

    public static ArrayList<GameObject> renderObjects = new ArrayList<>(5000);

    private BufferedImage floorSprite = (BufferedImage) Assets.getProperties("commonAssets").get("background0");
    private BufferedImage floorSprite1 = (BufferedImage) Assets.getProperties("commonAssets").get("background1");
    private static BufferedImage map = new BufferedImage(MainApp.WIDTH, MainApp.HEIGHT, BufferedImage.TYPE_INT_ARGB);
    public static Graphics2D mapG2 = (Graphics2D) map.getGraphics();
    public static ArrayList<BufferedImage> bloodSprites = new ArrayList<>();

    // TODO test variables
    private Random random = new Random();

    private int leftSpawnPoint = Grid.INDENT_LEFT;
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
        HashMap<String, Object> commonAssets = Assets.getProperties("effectsAssets");
        bloodSprites = (ArrayList<BufferedImage>) commonAssets.get("bloodSprites");

//        for (int i = 0; i < 720 / 40; i++) {
//            mapG2.drawImage(floorSprite, 0, i * 40, null);
//        }
        mapG2.drawImage(floorSprite, 0, 0, null);

        grid = new Grid();

//	    units.add(createDemoUnit("Orc Soldier", 800, 500));

//        createDemoUnit("Human Soldier", 50, 250);
//        units.add(createDemoUnit("Human Soldier", 0, 540));
//        units.add(createDemoUnit("Human Soldier", 500, 250));
//	    units.add(createDemoUnit("Orc Soldier", 850, 310));
//	    units.add(createDemoUnit("Human Archer", 200, 520));

//        createDemoUnit("Human Archer", 450, 250);
//        createDemoUnit("Human Archer", 450, 350);
//        createDemoUnit("Human Archer", 450, 440);
        createDemoUnit("Human Archer", 450, 550);
        createDemoUnit("Orc Archer", 900, 440);
    }

    public Unit createDemoUnit(String name, float x, float y) {
        HashMap<String, Object> props = Assets.getProperties(name);

        Side side = (Side) props.get("side");
        Unit unit = new Unit(name, side, GameObjectType.UNIT, x, y);

        AIContainer aic = new AIContainer(unit);

        GraphicsComponent gc = new UnitGraphicsComponent(unit);

        unit.setAIContainer(aic);
        unit.setGraphicsComponent(gc);

        grid.add(unit);

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
                mapG2.drawImage(floorSprite, 0, 0, null);
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

    @Override
    public void update() {
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

    @Override
    public void render(Graphics2D g2, double deltaTime) {
        g2.drawImage(map, 0, 0, null);
        if (DEBUG_GRID) {
            grid.render(g2, deltaTime);
        }

        renderObjects.clear();
        renderObjects.addAll(grid.getAllObjects());
        Collections.sort(renderObjects);

        for (GameObject obj : renderObjects) {
            obj.render(g2, deltaTime);
        }

        g2.drawImage(floorSprite1, 0, 0, null);

        g2.setPaint(Color.WHITE);
        g2.drawString("GameObjects: " + grid.getAllObjects().size() + " | Units: " + grid.amountOfUnits(), 5, 36);

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
//        createDemoUnit("Human Soldier", leftSpawnPoint, getRandomPointY());
//        createDemoUnit("Orc Soldier", rightSpawnPoint, getRandomPointY());

        if (++testSpawnTimer2 >= 8) {
//            createDemoUnit("Orc Soldier", rightSpawnPoint, getRandomPointY());
        }

        if (++testSpawnTimer2 >= 10) {
            testSpawnTimer2 = 0;
//            createDemoUnit("Orc Soldier", rightSpawnPoint, getRandomPointY());
            createDemoUnit("Orc Archer", rightSpawnPoint, getRandomPointY());
            createDemoUnit("Human Archer", leftSpawnPoint, getRandomPointY());
            createDemoUnit("Human Archer", leftSpawnPoint, getRandomPointY());
        }
    }

    private int getRandomPointY() {
        return (int) (random.nextFloat() * 500 + Grid.INDENT_TOP);
    }
}
