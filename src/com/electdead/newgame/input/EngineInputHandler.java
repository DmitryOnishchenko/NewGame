package com.electdead.newgame.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EngineInputHandler extends KeyAdapter {
	private KeyEvent keyEvent;
	
	public EngineInputHandler() {}
	
	@Override
	public void keyPressed(KeyEvent event) {
		keyEvent = event;
	}
	
	@Override
	public void keyReleased(KeyEvent event) {
		keyEvent = event;
	}
	
	public KeyEvent getKeyEvent() {
		return keyEvent;
	}
	
	public void clear() {
		keyEvent = null;
	}
}
