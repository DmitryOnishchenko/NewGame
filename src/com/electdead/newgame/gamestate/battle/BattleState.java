package com.electdead.newgame.gamestate.battle;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.engine.FastRemoveArrayList;
import com.electdead.newgame.engine.Grid;
import com.electdead.newgame.gameobjectV2.BasicGameObject;
import com.electdead.newgame.gameobjectV2.GameObject;
import com.electdead.newgame.gamestate.AbstractGameState;
import com.electdead.newgame.gamestate.GameStateManager;
import com.electdead.newgame.input.BattleStateInputHandler;
import com.electdead.newgame.main.MainApp;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class BattleState extends AbstractGameState {
    /* GridOld */
    public static Grid grid;

    /* Game objects */
    private List<GameObject> gameObjects = new FastRemoveArrayList<>(10_000);
//    private List<GameObject> renderObjects = new FastRemoveArrayList<>(10_000);

    /* Input handler */
    private BattleStateInputHandler inputHandler = new BattleStateInputHandler(this);

    /* Background graphics */ //TODO refactor
    private BufferedImage backgroundLevelZero
            = (BufferedImage) Assets.getProperties("commonAssets").get("background0");

    private BufferedImage backgroundLevelOne
            = (BufferedImage) Assets.getProperties("commonAssets").get("background1");

    private BufferedImage backgroundImage
            = new BufferedImage(MainApp.WIDTH, MainApp.HEIGHT, BufferedImage.TYPE_INT_ARGB);

    private Graphics2D backgroundGraphics = (Graphics2D) backgroundImage.getGraphics();

    /* Blood sprites */
    private ArrayList<BufferedImage> bloodSprites = new ArrayList<>();

    /* Menu */
    private DebugMenu debugMenu = new DebugMenu();

    public BattleState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
        grid = new Grid();

        /* Blood sprites */
        HashMap<String, Object> commonAssets = Assets.getProperties("effectsAssets");
        bloodSprites = (ArrayList<BufferedImage>) commonAssets.get("bloodSprites");

        backgroundGraphics.drawImage(backgroundLevelZero, 0, 0, null);

        /* Test */
//        createDemoUnit("Human Soldier", 200, 500);
//        createDemoUnit("Orc Soldier", 400, 500);
    }

    @Override
    public void processInput(KeyEvent event) {
        inputHandler.processInput(event);
    }

    @Override
    public void update() {
        if (BattleStateSettings.PAUSE) {
            return;
        }

        /* Dirty flag */
        if (BattleStateSettings.NEED_DELETE) {
            synchronized (gameObjects) {
                // delete objects
                Iterator<GameObject> iterator = gameObjects.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().delete) {
                        iterator.remove();
                    }
                }
            }
            BattleStateSettings.NEED_DELETE = false;
        }

        /* Dirty flag */
        if (BattleStateSettings.NEED_DELETE || BattleStateSettings.NEED_RELOCATE) {
            grid.checkDelete();
        }
        grid.update();


        if (BattleStateSettings.DEMO_MODE) {
            //TODO run demo
            demoMode();
        }
    }

    @Override
    public void render(Graphics2D g2, double deltaTime) {
        /* Background graphics: Level Zero */
        g2.drawImage(backgroundImage, 0, 0, null);

        /* Grid graphics */
        if (BattleStateSettings.DEBUG_GRID) {
            grid.render(g2, deltaTime);
        }

        //TODO render objects
//        renderObjects.clear();
//        renderObjects.addAll(gameObjects);
//        Collections.sort(renderObjects);

//        -Djava.util.Arrays.useLegacyMergeSort=true
//        renderObjects.clear();
//        synchronized (gameObjects) {
//            renderObjects.addAll(gameObjects);
//        }
//        Collections.sort(renderObjects);
//        for (GameObject gameObject: renderObjects) {
//            gameObject.render(g2, deltaTime);
//        }


        synchronized (gameObjects) {
            Collections.sort(gameObjects);
            for (GameObject gameObject: gameObjects) {
                gameObject.render(g2, deltaTime);
            }
        }

        /* Background graphics: Level One */
        g2.drawImage(backgroundLevelOne, 0, 0, null);

        /* Render info */
        g2.setPaint(Color.WHITE);
        g2.drawString("GameObjects: " + grid.getAllObjects().size() + " | Units: " + grid.size(), 5, 36);

        /* Render debug menu*/
        debugMenu.render(g2);
    }

    public void createDemoUnit(String name, float x, float y) {
        BasicGameObject gameObject = new BasicGameObject(name, x, y);

        synchronized (gameObjects) {
            gameObjects.add(gameObject);
        }
        grid.add(gameObject);
    }

    public void startNewGame() {
        synchronized (gameObjects) {
            gameObjects.clear();
        }
        grid.clear();
//        renderObjects.clear();

        backgroundGraphics.drawImage(backgroundLevelZero, 0, 0, null);
        BattleStateSettings.DEMO_MODE = false;
    }

    /* Benchmark-Demo test */
    public void demoMode() {
        createDemoUnit("Human Soldier", BattleStateSettings.leftSpawnPoint, getRandomPointY());
        createDemoUnit("Orc Soldier", BattleStateSettings.rightSpawnPoint, getRandomPointY());

        if (++BattleStateSettings.testSpawnTimer2 >= 8) {
            createDemoUnit("Orc Soldier", BattleStateSettings.rightSpawnPoint, getRandomPointY());
        }

        if (++BattleStateSettings.testSpawnTimer2 >= 10) {
            BattleStateSettings.testSpawnTimer2 = 0;
            createDemoUnit("Orc Soldier", BattleStateSettings.rightSpawnPoint, getRandomPointY());
            createDemoUnit("Orc Archer", BattleStateSettings.rightSpawnPoint, getRandomPointY());
            createDemoUnit("Human Archer", BattleStateSettings.leftSpawnPoint, getRandomPointY());
            createDemoUnit("Human Archer", BattleStateSettings.leftSpawnPoint, getRandomPointY());
        }
    }

    private Random random = new Random();
    public int getRandomPointY() {
        return (int) (random.nextFloat() * 490 + Grid.INDENT_TOP);
    }
}
