package com.electdead.newgame.gameobjects.components;

import java.awt.Graphics2D;

import com.electdead.newgame.gameobjects.GameObject;

public interface GraphicsComponent {
	public void update(GameObject obj);
	public void render(Graphics2D g2, double deltaTime);
}
