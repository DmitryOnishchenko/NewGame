package com.electdead.newgame.gamestate;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.gameobjects.GameObject;
import com.electdead.newgame.gameobjects.TypeObject;
import com.electdead.newgame.gameobjects.units.Unit;
import com.electdead.newgame.gameobjects.units.ai.AIContainer;
import com.electdead.newgame.graphics.GraphicsComponent;
import com.electdead.newgame.graphics.UnitGraphicsComponent;
import com.electdead.newgame.main.MainApp;
import com.electdead.newgame.physics.PhysicsComponent;
import com.electdead.newgame.physics.UnitPhysicsComponent;
import com.google.common.collect.TreeMultiset;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class DevGameState extends AbstractGameState {
    public static HashSet<GameObject> gameObjects = new HashSet<>();
    public static HashSet<Unit> units = new HashSet<>();
    public static TreeMultiset<GameObject> renderObjects = TreeMultiset.create();

    private BufferedImage floorSprite = (BufferedImage) Assets.getProperties("Battle background").get("floor");
    private static BufferedImage map = new BufferedImage(MainApp.WIDTH, MainApp.HEIGHT, BufferedImage.TYPE_INT_ARGB);
    public static Graphics2D floorG2 = (Graphics2D) map.getGraphics();

    private int testSpawnTimer;
    private int testSpawnTimer2;
    public static ArrayList<BufferedImage> bloodSprites = new ArrayList<>();

    private Random random = new Random();
    public static boolean SWARM = false;
    public static boolean DEBUG = false;

    public DevGameState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
        floorG2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < 720 / 40; i++) {
            floorG2.drawImage(floorSprite, 0, i * 40, null);
        }

        try {
            for (int i = 0; i < 8; i++) {
                BufferedImage img = ImageIO.read(MainApp.class.getResource("/res/blood/blood_" + i + ".png"));
                bloodSprites.add(img);
            }
        }	catch (IOException ex) { ex.printStackTrace(); }

//	    units.add(createDemoUnit("Orc Soldier", 800, 500));

//        units.add(createDemoUnit("Human Soldier", 200, 480));
//        units.add(createDemoUnit("Human Soldier", 0, 540));
//        units.add(createDemoUnit("Human Soldier", 500, 520));
//	    units.add(createDemoUnit("Human Archer", 400, 520));

//	    units.add(createDemoUnit("Human Archer", 200, 520));
//	    units.add(createDemoUnit("Orc Archer", 900, 300));
    }

    public Unit createDemoUnit(String name, float x, float y) {
        HashMap<String, Object> props = Assets.getProperties(name);

        TypeObject type = (TypeObject) props.get("type");
        Unit unit = new Unit(name, type, x, y);

        AIContainer aic = new AIContainer(unit);
//		unit.actions.add(aic.aiComponents[0]);

        PhysicsComponent pc = new UnitPhysicsComponent(unit);
        GraphicsComponent gc = new UnitGraphicsComponent(unit);

        unit.setAIContainer(aic);
        unit.setPhysicsComponent(pc);
        unit.setGraphicsComponent(gc);

        return unit;
    }

    @Override
    public void processInput(KeyEvent event) {
        if (event != null && event.getID() == KeyEvent.KEY_PRESSED) {
            if (event.getKeyChar() == 'a') {
                units.add(createDemoUnit("Human Soldier", -100, random.nextFloat() * 500 + 100));
            } else if (event.getKeyChar() == 's') {
                units.add(createDemoUnit("Human Archer", -100, random.nextFloat() * 500 + 100));
            } else if (event.getKeyChar() == 'k') {
                units.add(createDemoUnit("Orc Soldier", 1380, random.nextFloat() * 500 + 100));
            } else if (event.getKeyChar() == 'l') {
                units.add(createDemoUnit("Orc Archer", 1380, random.nextFloat() * 500 + 100));
            } else if (event.getKeyChar() == 'h') {
                SWARM = !SWARM;
            } else if (event.getKeyChar() == 'j') {
                DEBUG = !DEBUG;
            }
        }
    }

    @Override
    public void update() {
        for (GameObject obj : gameObjects)
            if (!obj.delete) obj.update();

        for (Unit unit : units)
            if (!unit.delete) unit.update();

        if (SWARM && ++testSpawnTimer > 25) {
            SWARM();
            testSpawnTimer = 0;
        }

        for (Iterator<GameObject> iterator = gameObjects.iterator(); iterator.hasNext();) {
            GameObject gameObject = iterator.next();
            if (gameObject.delete)
                iterator.remove();
        }

        for (Iterator<Unit> iterator = units.iterator(); iterator.hasNext();) {
            Unit unit = iterator.next();
            if (unit.delete) {
                iterator.remove();
            }
        }
    }

    @Override
    public void render(Graphics2D g2, double deltaTime) {
        g2.drawImage(map, 0, 0, null);

        renderObjects.clear();
        renderObjects.addAll(gameObjects);
        renderObjects.addAll(units);

        for (GameObject obj : renderObjects)
            if (obj.visible) obj.render(g2, deltaTime);

        g2.setPaint(Color.WHITE);
        g2.drawString("GameObjects: " + gameObjects.size() + " | Units: " + units.size(), 5, 36);

        g2.drawString("To spawn Human Soldier press \"A\"", 5, 72);
        g2.drawString("To spawn Human Archer press \"S\"", 5, 90);
        g2.drawString("To spawn Orc Soldier press \"K\"", 1100, 72);
        g2.drawString("To spawn Orc Archer press \"L\"", 1100, 90);
        g2.drawString("To activate Debug mode press \"J\"", 580, 72);
        g2.drawString("To start Demo Mode press \"H\"", 580, 90);
    }

    public void SWARM() {
        int width = 500;
        Random r = new Random();
        Unit humanUnit1 = createDemoUnit("Human Soldier", -100, r.nextFloat() * width + 150);
        units.add(humanUnit1);

        Unit orcUnit1 = createDemoUnit("Orc Soldier", 1380, r.nextFloat() * width + 150);
        units.add(orcUnit1);

        if (++testSpawnTimer2 >= 10) {
            Unit orcUnit2 = createDemoUnit("Orc Soldier", 1380, r.nextFloat() * width + 150);
            units.add(orcUnit2);
        }

        if (++testSpawnTimer2 >= 10) {
            testSpawnTimer2 = 0;
            Unit orcUnit2 = createDemoUnit("Orc Soldier", 1380, r.nextFloat() * width + 150);
            units.add(orcUnit2);

            Unit orcUnit3 = createDemoUnit("Orc Archer", 1380, r.nextFloat() * width + 150);
            units.add(orcUnit3);

            Unit humanUnit2 = createDemoUnit("Human Archer", -100, r.nextFloat() * width + 150);
            units.add(humanUnit2);
        }
    }
}
