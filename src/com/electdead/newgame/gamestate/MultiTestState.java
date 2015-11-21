package com.electdead.newgame.gamestate;

import com.electdead.newgame.engine.EngineV2;
import com.electdead.newgame.gameobjectV2.GameObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiTestState extends AbstractGameState {
    private List<GameObject> gameObjects = new ArrayList<>();

    public MultiTestState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
//        for (int i = 0; i < 20_000; i++) {
//            TestGameObject test = new TestGameObject();
//            gameObjects.add(test);
//        }

        setUp();
    }

    public void setUp() {
        EngineV2.gameObjects = gameObjects;
    }

    public void tearDown() {
        EngineV2.gameObjects = Collections.emptyList();
    }

    @Override
    public void processInput(KeyEvent event) {
        if (event != null) {
            gameObjects.add(new TestGameObject());
//            EngineV2.inputHandled();
        }
    }

    @Override
    public void update() {
//        EngineV2.startProcess(gameObjects.size());
    }

    @Override
    public void render(Graphics2D g2, double deltaTime) {
        for (GameObject gameObject: gameObjects) {
            gameObject.render(g2, deltaTime);
        }
    }
}
