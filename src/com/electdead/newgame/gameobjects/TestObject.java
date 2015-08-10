package com.electdead.newgame.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import com.electdead.newgame.engine.EngineV1;

public class TestObject {
	public double speed;
	public double x;
	public double y;
	public int width;
	public int height;
	public Color color;
	
	public TestObject(double aX, double aY, int aWidth, int aHeight, double aSpeed) {
		x = aX;
		y = aY;
		width = aWidth;
		height = aHeight;
		speed = (aSpeed * EngineV1.MS_PER_UPDATE) / 1000;
		Random r = new Random();
		color = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256), r.nextInt(256));
	}
	
	public void update() {
		if (x + 20 >= 1280 || x <= 0) {
			speed = -speed;
		}
		x += speed;
		y += speed;
	}
	
	public void render(Graphics2D g2, double deltaTime) {
		g2.setPaint(color);
		g2.fillRect((int) (x * deltaTime), (int) (y * deltaTime), width, height);
	}
}
