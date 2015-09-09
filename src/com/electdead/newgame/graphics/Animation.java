package com.electdead.newgame.graphics;

import java.awt.image.BufferedImage;

import com.electdead.newgame.gameobjects.units.actions.Action;

public class Animation {
	public Action action;
	private int currentSprite = 0;
	private int animationTimer;
	private BufferedImage[] sprites;
	
	public Animation(Action action, BufferedImage[] sprites) {
		this.action = action;
		this.sprites = sprites;
		currentSprite = 0;
	}
	
	public void next() {
		if (++animationTimer >= action.unit.graphModel.getAnimationSpeed()) {
			currentSprite++;
	    	if (currentSprite == sprites.length) {
	    		currentSprite = 0;
	    		if (action.needFullAnimation) 
	    			action.animationFinished();
	    	}
	    	animationTimer = 0;
	    }
	}
	
	public BufferedImage get() {
		return sprites[currentSprite];
	}
	
	public boolean isLast() {
		return currentSprite == sprites.length;
	}
}
