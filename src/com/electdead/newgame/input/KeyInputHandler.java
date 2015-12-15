package com.electdead.newgame.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInputHandler extends KeyAdapter {
	private volatile KeyEvent keyEvent;
	
	public KeyInputHandler() {}
	
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
