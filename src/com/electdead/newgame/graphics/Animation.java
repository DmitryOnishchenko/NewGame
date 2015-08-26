package com.electdead.newgame.graphics;

import java.awt.image.BufferedImage;

import com.electdead.newgame.gameobjects.actions.Action;

public class Animation {
	public Action action;
	private int currentSprite;
	private int animationTimer;
	private BufferedImage[] sprites;
	
	public Animation(Action action, BufferedImage[] sprites) {
		this.action = action;
		this.sprites = sprites;
		currentSprite = sprites.length - 1;
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
