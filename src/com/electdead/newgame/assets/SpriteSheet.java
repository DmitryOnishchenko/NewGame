package com.electdead.newgame.assets;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	private BufferedImage spriteSheet;
	
	public SpriteSheet(BufferedImage spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	public BufferedImage getTile(int x, int y, int width, int height) {
		BufferedImage tile = spriteSheet.getSubimage(x, y, width, height);
		return tile;
	}
	
	public int getWidth() {
		return spriteSheet.getWidth();
	}
	
	public int getHeight() {
		return spriteSheet.getHeight();
	}
}
