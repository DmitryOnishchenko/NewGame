package com.electdead.newgame.gameobjects.components;

import java.awt.image.BufferedImage;

import com.electdead.newgame.engine.EngineV1;

public class GraphicsModel {
	private int widthSprite;
	private int heightSprite;
	private int baseLine;
	private BufferedImage[] moveSprites;
	private BufferedImage[] fightSprites;
	private BufferedImage[] dieSprites;
	private int animationSpeed;
	
	public GraphicsModel() {}
	
	/* Getters */
	public int getWidthSprite() { return widthSprite; }

	public int getHeightSprite() { return heightSprite; }

	public int getBaseLine() { return baseLine; }

	public BufferedImage[] getMoveSprites() { return moveSprites; }

	public BufferedImage[] getFightSprites() { return fightSprites; }

	public BufferedImage[] getDieSprites() { return dieSprites; }

	public int getAnimationSpeed() { return animationSpeed; }
	
	/* Setters */
	public void setWidthSprite(int widthSprite) { this.widthSprite = widthSprite; }

	public void setHeightSprite(int heightSprite) { this.heightSprite = heightSprite; }

	public void setBaseLine(int baseLine) { this.baseLine = baseLine; }

	public void setMoveSprites(BufferedImage[] moveSprites) { this.moveSprites = moveSprites; }

	public void setFightSprites(BufferedImage[] fightSprites) { this.fightSprites = fightSprites; }

	public void setDieSprites(BufferedImage[] dieSprites) { this.dieSprites = dieSprites; }
	
	public void setAnimationSpeed(int animationSpeedMs) { this.animationSpeed = animationSpeedMs / EngineV1.MS_PER_UPDATE; }
}
