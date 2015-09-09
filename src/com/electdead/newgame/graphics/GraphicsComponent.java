package com.electdead.newgame.graphics;

import java.awt.Graphics2D;

public interface GraphicsComponent {
	public void update();
	public void render(Graphics2D g2, double deltaTime);
}
