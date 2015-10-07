package com.electdead.newgame.gamestate;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.assets.ImageUtils;
import com.electdead.newgame.engine.Grid;
import com.electdead.newgame.gameobjects.GameObject;
import com.electdead.newgame.gameobjects.GameObjectType;
import com.electdead.newgame.gameobjects.Side;
import com.electdead.newgame.gameobjects.units.Unit;
import com.electdead.newgame.gameobjects.units.ai.AIContainer;
import com.electdead.newgame.graphics.GraphicsComponent;
import com.electdead.newgame.graphics.UnitGraphicsComponent;
import com.electdead.newgame.main.MainApp;
import com.electdead.newgame.physics.PhysicsComponent;
import com.electdead.newgame.physics.UnitPhysicsComponent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class DevGameState extends AbstractGameState {
    public static Grid grid;

    public static ArrayList<GameObject> gameObjects = new ArrayList<>();
    public static ArrayList<Unit> units = new ArrayList<>();
//    public static TreeMultiset<GameObject> renderObjects = TreeMultiset.create();
    public static ArrayList<GameObject> renderObjects = new ArrayList<>(5000);

    private BufferedImage floorSprite = (BufferedImage) Assets.getProperties("Battle background").get("floor");
    private static BufferedImage map = new BufferedImage(MainApp.WIDTH, MainApp.HEIGHT, BufferedImage.TYPE_INT_ARGB);
    public static Graphics2D floorG2 = (Graphics2D) map.getGraphics();

    private int testSpawnTimer;
    private int testSpawnTimer2;
    public static ArrayList<BufferedImage> bloodSprites = new ArrayList<>();

    private int leftSpawnPoint = Grid.INDENT_LEFT;
    private int rightSpawnPoint = MainApp.WIDTH + 50;

    // TODO test variables
    private Random random = new Random();
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
//        floorG2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < 720 / 40; i++) {
            floorG2.drawImage(floorSprite, 0, i * 40, null);
        }

        try {
            for (int i = 0; i < 8; i++) {
                BufferedImage img = ImageIO.read(MainApp.class.getResource("/res/blood/blood_" + i + ".png"));
                img = ImageUtils.resizeImage(img, Assets.SCALE);
                bloodSprites.add(img);
            }
        }	catch (IOException ex) { ex.printStackTrace(); }

        /* Grid */
        grid = new Grid();

//	    units.add(createDemoUnit("Orc Soldier", 800, 500));

//        createDemoUnit("Human Soldier", 50, 250);
//        units.add(createDemoUnit("Human Soldier", 0, 540));
//        units.add(createDemoUnit("Human Soldier", 500, 250));
//	    units.add(createDemoUnit("Orc Soldier", 850, 310));
	    units.add(createDemoUnit("Human Archer", 380, 500));

//	    units.add(createDemoUnit("Human Archer", 200, 520));
	    units.add(createDemoUnit("Orc Archer", 900, 300));
    }

    public Unit createDemoUnit(String name, float x, float y) {
        HashMap<String, Object> props = Assets.getProperties(name);

        Side side = (Side) props.get("side");
        Unit unit = new Unit(name, side, GameObjectType.UNIT, x, y);

        AIContainer aic = new AIContainer(unit);
//		unit.actions.add(aic.aiComponents[0]);

        PhysicsComponent pc = new UnitPhysicsComponent(unit);
        GraphicsComponent gc = new UnitGraphicsComponent(unit);

        unit.setAIContainer(aic);
        unit.setPhysicsComponent(pc);
        unit.setGraphicsComponent(gc);

        grid.add(unit);

        return unit;
    }

    @Override
    public void processInput(KeyEvent event) {
        if (event != null && event.getID() == KeyEvent.KEY_PRESSED) {
            if (event.getKeyChar() == 'a') {
                createDemoUnit("Human Soldier", leftSpawnPoint, getRandomPointY());
            } else if (event.getKeyChar() == 's') {
                units.add(createDemoUnit("Human Archer", leftSpawnPoint, getRandomPointY()));
            } else if (event.getKeyChar() == 'k') {
                createDemoUnit("Orc Soldier", rightSpawnPoint, getRandomPointY());
            } else if (event.getKeyChar() == 'l') {
                units.add(createDemoUnit("Orc Archer", rightSpawnPoint, getRandomPointY()));
            } else if (event.getKeyChar() == 'h') {
                SWARM = !SWARM;
            } else if (event.getKeyChar() == ' ') {
                PAUSE = !PAUSE;
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

        g2.setPaint(Color.WHITE);
        g2.drawString("GameObjects: " + grid.getAllObjects().size() + " | Units: " + grid.amountOfUnits(), 5, 36);

        g2.drawString("To spawn Human Soldier press \"A\"", 5, 72);
        g2.drawString("To spawn Human Archer press \"S\"", 5, 90);
        g2.drawString("To spawn Orc Soldier press \"K\"", 1100, 72);
        g2.drawString("To spawn Orc Archer press \"L\"", 1100, 90);
        g2.drawString("To start Demo Mode press \"H\"", 580, 90);
        g2.drawString("PAUSE - \"Space\"", 580, 108);

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

        g2.setPaint(Color.BLUE);
        g2.drawString("To activate grid: \"3\"", 700, stringY);
        if (DEBUG_GRID) {
            g2.drawRect(696, rectY, 200, 20);
        }
    }

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
        return (int) (random.nextFloat() * 500 + Grid.INDENT_TOP);
    }
}
