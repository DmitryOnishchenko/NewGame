package com.electdead.newgame.gamestate;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.engine.EngineV2;
import com.electdead.newgame.engine.FastRemoveArrayList;
import com.electdead.newgame.engine.Grid;
import com.electdead.newgame.gameobject.GameObjectType;
import com.electdead.newgame.gameobject.Side;
import com.electdead.newgame.gameobjectV2.BasicGameObject;
import com.electdead.newgame.gameobjectV2.GameObject;
import com.electdead.newgame.input.BattleStateInputHandler;
import com.electdead.newgame.main.MainApp;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class BattleState extends AbstractGameState {
    /* Grid */
    private Grid grid;

    /* Input handler */
    private BattleStateInputHandler inputHandler = new BattleStateInputHandler(this);

    /* Game objects */
    private List<BasicGameObject> gameObjects    = new FastRemoveArrayList<>(5000);
    private List<BasicGameObject> renderObjects  = new FastRemoveArrayList<>(5000);

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

        int needToProcess = grid.size();
        if (needToProcess != 0) {
            EngineV2.startProcess(needToProcess);
        }

        if (BattleStateSettings.DEMO_MODE) {
            //TODO run demo
        }
    }

    @Override
    public void render(Graphics2D g2, double deltaTime) {
        /* Background graphics: Level Zero */
        g2.drawImage(backgroundImage, 0, 0, null);

        //TODO render objects
        renderObjects.clear();
        renderObjects.addAll(gameObjects);
        Collections.sort(renderObjects);
        for (GameObject gameObject: renderObjects) {
            gameObject.render(g2, deltaTime);
        }

        /* Background graphics: Level One */
        g2.drawImage(backgroundLevelOne, 0, 0, null);

        /* Render info */
        g2.setPaint(Color.WHITE);
        g2.drawString("GameObjects: " + grid.getAllObjects().size() + " | Units: " + grid.size(), 5, 36);

        /* Render manual*/
        drawManualMenu(g2);
    }

    private void drawManualMenu(Graphics2D g2) {
        g2.drawString("To spawn Human Soldier press \"A\"", 100, 72);
        g2.drawString("To spawn Human Archer press \"S\"", 100, 90);

        g2.drawString("Exit - \"Esc\"", 480, 72);
        if (!BattleStateSettings.DEMO_MODE) {
            g2.drawString("To start Demo Mode press \"H\"", 580, 90);
        } else {
            g2.setPaint(Color.RED);
            g2.drawString("To stop Demo Mode press \"H\"", 580, 90);
        }
        if (!BattleStateSettings.PAUSE) {
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
        if (BattleStateSettings.DEBUG_MODE) {
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
        if (BattleStateSettings.DEBUG_BOX) {
            g2.drawRect(296, rectY, 200, 20);
        }

        g2.setPaint(Color.YELLOW);
        g2.drawString("To activate target tracking: \"2\"", 500, stringY);
        if (BattleStateSettings.DEBUG_TARGET) {
            g2.drawRect(496, rectY, 200, 20);
        }

        g2.setPaint(Color.GREEN);
        g2.drawString("To activate grid: \"3\"", 700, stringY);
        if (BattleStateSettings.DEBUG_GRID) {
            g2.drawRect(696, rectY, 200, 20);
        }
    }

    public BasicGameObject createDemoUnit(String name, float x, float y) {
        HashMap<String, Object> props = Assets.getProperties(name);

        Side side = (Side) props.get("side");
        BasicGameObject gameObject = new BasicGameObject(name, x, y, side, GameObjectType.UNIT);

        grid.add(gameObject);

        return gameObject;
    }

    public void startNewGame() {
        grid.clear();
        backgroundGraphics.drawImage(backgroundLevelZero, 0, 0, null);
        BattleStateSettings.DEMO_MODE = false;
    }
}
