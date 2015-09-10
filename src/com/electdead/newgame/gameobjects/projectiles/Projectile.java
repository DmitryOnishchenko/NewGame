package com.electdead.newgame.gameobjects.projectiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.gameobjects.GameObject;
import com.electdead.newgame.gameobjects.TypeObject;
import com.electdead.newgame.physics.Vector2F;

public class Projectile extends GameObject {
	public Vector2F dir;
	public float speed = 5;
	public BufferedImage sprite = (BufferedImage) Assets.getProperties("projectiles").get("woodenArrow");
	
	public Projectile(String name, TypeObject type, float x, float y) {
		super(name, type, x, y);
	}
	
	@Override
	public void update() {
		float shiftX = speed * dir.x;
		pos.x += shiftX;
		
		float shiftY = speed * dir.y;
		pos.y += shiftY;
		
		checkDelete();
	}
	
	@Override
	public void render(Graphics2D g2, double deltaTime) {
		g2.drawImage(sprite, (int) pos.x, (int) pos.y, null);
	}
}
