package com.electdead.newgame.engine;

import java.awt.Dimension;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class AbstractGameLoop extends JPanel implements Runnable {
	protected int width;
	protected int height;
	protected int maxFps;
	protected int msPerFrame;
	
	/*INFO*/
	protected boolean showInfo = true;
	protected int tps = 0;
	protected int tpsInfo = 0;
	protected int fps = 0;
	protected int fpsInfo = 0;
	
	public AbstractGameLoop(int width, int height, int fps) {
		this.width = width;
		this.height = height;
		this.maxFps = fps;
		this.msPerFrame = 1000 / fps;
		
		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
		requestFocus();
	}
		
	public void start() {
		new Thread(this).start();
	}	
	
	public abstract void run();
	public abstract void processInput();
	public abstract void update();
	public abstract void render(double deltaTime);
}
