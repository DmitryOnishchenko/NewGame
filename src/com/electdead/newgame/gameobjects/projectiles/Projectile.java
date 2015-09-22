package com.electdead.newgame.gameobjects.projectiles;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.gameobjects.GameObject;
import com.electdead.newgame.gameobjects.TypeObject;
import com.electdead.newgame.physics.Vector2F;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile extends GameObject {
	public Vector2F moveDir;
	public float speed = 5;
	public BufferedImage sprite = (BufferedImage) Assets.getProperties("projectiles").get("woodenArrow");
	
	public Projectile(String name, TypeObject type, float x, float y) {
		super(name, type, x, y);
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
