package com.electdead.newgame.main;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.electdead.newgame.engine.AbstractGameLoop;
import com.electdead.newgame.engine.EngineV1;

public class MainApp {
//	public static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//	public static int width = gd.getDisplayMode().getWidth();
//	public static int height = gd.getDisplayMode().getHeight();
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame gameWindow = new GameWindow("Test game v0.1", WIDTH, HEIGHT);
				AbstractGameLoop engine = new EngineV1(WIDTH, HEIGHT);
				gameWindow.add(engine);
				gameWindow.setVisible(true);
				engine.start();
			}
		});
	}
}
