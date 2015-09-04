package com.electdead.newgame.gamestate;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public abstract class AbstractGameState {
	public GameStateManager gsm;
	
	public AbstractGameState(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	public abstract void init();
	public abstract void processInput(KeyEvent event);
	public abstract void update();
	public abstract void render(Graphics2D g2, double deltaTime);
}
