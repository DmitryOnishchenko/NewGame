package com.electdead.newgame.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import com.electdead.newgame.gamestate.GameStateManager;
import com.electdead.newgame.input.InputHandler;

@SuppressWarnings("serial")
public class EngineV1 extends AbstractGameLoop {
	/* Graphics */
	public static final int MAX_FPS = 120;
	public static final int MS_PER_UPDATE = 10;
	public static final int UPDATE_PER_SEC = 1000 / MS_PER_UPDATE;
	public static final boolean useFpsLimit = true;
	private BufferedImage frameImg;
	private Graphics2D g2;

	/* Managers */
	private GameStateManager gsm;
	
	public EngineV1(int width, int height) {
		super(width, height, MAX_FPS); 
		init();
	}

	public void init() {
		addKeyListener(new InputHandler());
		gsm = new GameStateManager();
		gsm.init();
		frameImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) frameImg.getGraphics();
		/* Test for text */
//		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		/* Test for shapes */
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	@Override
	public void processInput() {
		gsm.processInput();
	}
	
	@Override
	public void update() {
		gsm.update();
		tps++;
	}
		
	@Override
	public void render(double deltaTime) {
		/* Clear frame */
		g2.clearRect(0, 0, width, height);
		
		/* Render state */
		gsm.render(g2, deltaTime);
		
		/* FPS/TPS info */
		if (showInfo) {
			fps++;
			g2.setPaint(Color.WHITE);
			g2.drawString("FPS: " + fpsInfo + " | TPS: " + tpsInfo, 5, 15);			
		}
		
		/* Draw frame */
		Graphics graphics = getGraphics();
		graphics.drawImage(frameImg, 0, 0, null);
		graphics.dispose();
		
	}
	
	@Override
    public void run() {
		long timer = getCurrentTime();
		long previous = getCurrentTime();
		long lag = 0;
		
		while (true) {
			long current = getCurrentTime();
			long elapsed = current - previous;
			previous = current;
			lag += elapsed;
			
			processInput();
			
			while (lag >= MS_PER_UPDATE) {
				update();
				lag -= MS_PER_UPDATE;
			}
			
			double deltaTime = 1 - lag / MS_PER_UPDATE;
			render(deltaTime);
			
			if (getCurrentTime() - timer >= 1000) {
				tpsInfo = tps;
				tps = 0;
				fpsInfo = fps;
				fps = 0;
				timer += 1000;
			}
			
			if (useFpsLimit) {
				long sleepFor = current + msPerFrame - getCurrentTime();
				try {
					if (sleepFor > 0) Thread.sleep(sleepFor); 
				} catch (InterruptedException ex) { ex.printStackTrace(); }				
			}
		}
    }
	
	private long getCurrentTime() {
		return System.currentTimeMillis();
	}
}
