package com.electdead.newgame.gameobjects.projectiles;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.engine.EngineV1;
import com.electdead.newgame.gameobjects.GameObject;
import com.electdead.newgame.gameobjects.Side;
import com.electdead.newgame.physics.Vector2F;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile extends GameObject {
	public Vector2F moveDir;
	public float speed = 700 / EngineV1.UPDATES_PER_SEC;
	public BufferedImage sprite = (BufferedImage) Assets.getProperties("projectiles").get("woodenArrow");
	
	public Projectile(String name, Side side, float x, float y) {
		super(name, side, x, y);
	}
	
	@Override
	public void update() {
		float shiftX = speed * moveDir.x;
		pos.x += shiftX;
		
		float shiftY = speed * moveDir.y;
		pos.y += shiftY;
		
		checkDelete();
	}
	
	@Override
	public void render(Graphics2D g2, double deltaTime) {
		g2.drawImage(sprite, (int) pos.x, (int) pos.y, null);
	}
}
