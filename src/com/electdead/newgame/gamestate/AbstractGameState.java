package com.electdead.newgame.gamestate;

import java.awt.Graphics2D;

public abstract class AbstractGameState {
	public GameStateManager gsm;
	
	public AbstractGameState(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	public abstract void init();
	public abstract void processInput();
	public abstract void update();
	public abstract void render(Graphics2D g2, double deltaTime);
}
