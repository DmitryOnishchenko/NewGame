package com.electdead.newgame.gameobjects;

import java.awt.Graphics2D;

import com.electdead.newgame.gameobjects.ai.AIContainer;
import com.electdead.newgame.gameobjects.components.GraphicsComponent;
import com.electdead.newgame.gameobjects.components.InputComponent;
import com.electdead.newgame.gameobjects.components.PhysicsComponent;
import com.electdead.newgame.physics.Vector2F;
import com.google.common.base.Joiner;

public abstract class GameObject implements Comparable<GameObject> {
	public static int ID = 0;
	
	public final int id;
	public String name;
	public Vector2F pos;
	
//	public double x;
//	public double y;
	public TypeObject type;
	public boolean visible = true;
	public boolean delete = false;
	
	private InputComponent inputComponent;
	private AIContainer aiContainer;
	private PhysicsComponent physicsComponent;
	private GraphicsComponent graphicsComponent;
	
	public GameObject(String name, TypeObject type, float x, float y) {
		this.id = ++ID;
		this.name = name;
		this.pos = new Vector2F(x, y);
		this.type = type;
	}
	
	public void setAIContainer(AIContainer aic) {
		this.aiContainer = aic;
	}
	
	public void setPhysicsComponent(PhysicsComponent pc) {
		this.physicsComponent = pc;
	}
	
	public void setGraphicsComponent(GraphicsComponent gc) {
		this.graphicsComponent = gc;
	}
	
	public InputComponent getInput() {
		return inputComponent;
	} 
	
	public AIContainer getAI() {
		return aiContainer;
	}
	
	public PhysicsComponent getPhys() {
		return physicsComponent;
	}
	
	public GraphicsComponent getGraph() {
		return graphicsComponent;
	}
	
	public int x() {
		return (int) pos.x;
	}
	
	public int y() {
		return (int) pos.y;
	}
	
	public void update() {
//		inputComponent.update();
//		if (aiComponent != null) aiComponent.update(this);
		if (physicsComponent != null) physicsComponent.update();
		if (graphicsComponent != null) graphicsComponent.update();
	}
	
	public void render(Graphics2D g2, double deltaTime) {
		if (graphicsComponent != null) graphicsComponent.render(g2, deltaTime);
	}
	
	public String toString() {
		return Joiner.on("").join("GameObject[x: ", pos.x, ", y:", pos.y,"]");
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
