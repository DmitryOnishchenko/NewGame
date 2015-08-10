package com.electdead.newgame.gameobjects;

import java.awt.Graphics2D;

import com.electdead.newgame.gameobjects.components.StupidSoldierAIComponent;
import com.electdead.newgame.gameobjects.components.GraphicsComponent;
import com.electdead.newgame.gameobjects.components.InputComponent;
import com.electdead.newgame.gameobjects.components.PhysicsComponent;
import com.google.common.base.Joiner;

public class GameObject implements Comparable<GameObject> {
	public static int ID = 0;
	
	public int id;
	public String name;
	public double x;
	public double y;
	public TypeObject type;
	public boolean visible = true;
	public boolean delete = false;
	
	private InputComponent inputComponent;
	private StupidSoldierAIComponent aiComponent;
	private PhysicsComponent physicsComponent;
	private GraphicsComponent graphicsComponent;
	
	public GameObject(String name, TypeObject type, double x, double y) {
		this.id = ++ID;
		this.name = name;
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public void init(StupidSoldierAIComponent aic, PhysicsComponent pc, GraphicsComponent gc) {
		aiComponent = aic;
		physicsComponent = pc;
		graphicsComponent = gc;
	}
	
	public InputComponent getInput() {
		return inputComponent;
	} 
	
	public StupidSoldierAIComponent getAI() {
		return aiComponent;
	}
	
	public PhysicsComponent getPhys() {
		return physicsComponent;
	}
	
	public GraphicsComponent getGraph() {
		return graphicsComponent;
	}
	
	public int x() {
		return (int) x;
	}
	
	public int y() {
		return (int) y;
	}
	
	public void update() {
//		inputComponent.update();
		aiComponent.update();
		physicsComponent.update();
		graphicsComponent.update();
	}
	
	public void render(Graphics2D g2, double deltaTime) {
		graphicsComponent.render(g2, deltaTime);
	}
	
	public String toString() {
		return Joiner.on("").join("GameObject[x: ", x, ", y:", y,"]");
	}
	
	public int compareTo(GameObject obj) {
		int result = y() - obj.y();
		if (result == 0) {
			result = x() - obj.x();
		}
		if (result == 0) result = id - obj.id;
		
		return result;
	}
}
