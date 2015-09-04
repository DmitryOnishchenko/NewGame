package com.electdead.newgame.input;

import java.awt.event.KeyEvent;

public abstract class InputHandler {
	
	public InputHandler()  {}
	
	public abstract void processInput(KeyEvent event);
}
