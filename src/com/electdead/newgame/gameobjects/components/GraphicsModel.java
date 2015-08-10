package com.electdead.newgame.gameobjects.components;

import java.awt.image.BufferedImage;

import com.electdead.newgame.engine.EngineV1;

public class GraphicsModel {
	private int widthSprite;
	private int heightSprite;
	private int baseLine;
	private int defaultSpriteIndex;
	private int moveSprites;
	private int fightSprites;
	private int dieSprites;
	private BufferedImage[] sprites;
	private BufferedImage[] moveAnimation;
	private BufferedImage[] fightAnimation;
	private BufferedImage[] dieAnimation;
	private int animationSpeed;
	
	public GraphicsModel() {}
	
	/* Getters */
	public int getWidthSprite() { return widthSprite; }

	public int getHeightSprite() { return heightSprite; }

	public int getBaseLine() { return baseLine; }

	public int getDefaultSpriteIndex() { return defaultSpriteIndex; }

	public int getMoveSprites() { return moveSprites; }

	public int getFightSprites() { return fightSprites; }

	public int getDieSprites() { return dieSprites; }

	public BufferedImage[] getSprites() { return sprites; }
	
	public BufferedImage getSprite(int currentSprite) {
		return sprites[currentSprite];
	}

	public int getAnimationSpeedMs() { return animationSpeed; }
	
	/* Setters */
	public void setWidthSprite(int widthSprite) { this.widthSprite = widthSprite; }

	public void setHeightSprite(int heightSprite) { this.heightSprite = heightSprite; }

	public void setBaseLine(int baseLine) { this.baseLine = baseLine; }

	public void setDefaultSpriteIndex(int defaultSpriteIndex) { this.defaultSpriteIndex = defaultSpriteIndex; }

	public void setMoveSprites(int moveSprites) { this.moveSprites = moveSprites; }

	public void setFightSprites(int fightSprites) { this.fightSprites = fightSprites; }

	public void setDieSprites(int dieSprites) { this.dieSprites = dieSprites; }

	public void setSprites(BufferedImage[] sprites) { 
		this.sprites = sprites;
	}

	public void setAnimationSpeedMs(int animationSpeedMs) { this.animationSpeed = animationSpeedMs / EngineV1.MS_PER_UPDATE; }
}
