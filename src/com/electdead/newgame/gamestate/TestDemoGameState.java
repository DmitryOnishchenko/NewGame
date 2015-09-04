package com.electdead.newgame.gamestate;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import com.electdead.newgame.gameobjects.TestObject;

public class TestDemoGameState extends AbstractGameState {
	public int size;
	public ArrayList<TestObject> list;
	
	public TestDemoGameState(GameStateManager gsm, int aSize) {
		super(gsm);
		size = aSize;
		list = new ArrayList<>(size);
	}

	@Override
    public void init() {
		for (int i = 0; i < size; i++) {
			Random r = new Random();
			int x = r.nextInt(1000);
			int y = 15 + r.nextInt(600);
			int w = 10 + r.nextInt(40);
			int h = 10 + r.nextInt(40);
			double s = 200 + r.nextInt(400);
			TestObject obj = new TestObject(x, y, w, h, s);
			list.add(obj);
		}
	}

	@Override
	public void processInput(KeyEvent event) {
		
	}
	
	@Override
    public void update() {
		for (TestObject obj : list) {
			obj.update();
		}
    }

	@Override
    public void render(Graphics2D g2, double deltaTime) {
		for (TestObject obj : list) {
			obj.render(g2, deltaTime);			
		}
    }

}
