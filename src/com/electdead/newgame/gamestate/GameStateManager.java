package com.electdead.newgame.gamestate;

import com.electdead.newgame.gamestate.battle.BattleState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Stack;

public class GameStateManager {
    private Stack<AbstractGameState> states;

    public GameStateManager() {
        states = new Stack<>();
//        states.push(new DevGameState(this));
//        states.push(new MultiTestState(this));
        states.push(new BattleState(this));
    }

    public void init() {
        states.peek().init();
    }

    public void processInput(KeyEvent event) {
        states.peek().processInput(event);
    }

    public void update() {
        states.peek().update();
    }

    public void render(Graphics2D graphics, double deltaTime) {
        states.peek().render(graphics, deltaTime);
    }
}
