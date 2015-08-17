package com.electdead.newgame.gamestate;

import java.awt.Graphics2D;
import java.util.Stack;

public class GameStateManager {
	private Stack<AbstractGameState> states;
	
	public GameStateManager() {
		states = new Stack<>();
		states.push(new DevGameState(this));
//		states.push(new TestDemoGameState(this, 300));
	}
	
	public void init() {
		states.peek().init();
	}
	
	public void processInput() {
		states.peek().processInput();
	}
	
	public void update() {
		states.peek().update();
	}
	
	public void render(Graphics2D g2, double deltaTime) {
		states.peek().render(g2, deltaTime);
	}
}
