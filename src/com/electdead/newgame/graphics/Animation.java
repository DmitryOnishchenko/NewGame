package com.electdead.newgame.graphics;

import java.awt.image.BufferedImage;

import com.electdead.newgame.gameobjects.UnitState;

public class Animation {
	private UnitState nextState;
//	private int currentSprite = 0;
	private int animationTimer;
	private int animationSpeed;
	private BufferedImage[] sprites;
	private boolean interrupted;
	
	public Animation(BufferedImage[] sprites, int animationTimer, int animationSpeed, boolean interrupted) {
		this.sprites = sprites;
		this.animationTimer = animationTimer;
		this.animationSpeed = animationSpeed;
		this.interrupted = interrupted;
	}
	
	public void next(int currentSprite) {
		if (++animationTimer >= animationSpeed) {
	    	currentSprite++;
	    	if (currentSprite == sprites.length) {
	    		currentSprite = 0;
	    	}
	    	animationTimer = 0;
	    }
	}
	
	public BufferedImage get(int currentSprite) {
		return sprites[currentSprite];
	}
}
