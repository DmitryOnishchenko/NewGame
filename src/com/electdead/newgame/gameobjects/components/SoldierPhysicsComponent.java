package com.electdead.newgame.gameobjects.components;

import com.electdead.newgame.gameobjects.GameObject;

public class SoldierPhysicsComponent implements PhysicsComponent {
	private GameObject object;
	private double speed = 0.48;
	
	public SoldierPhysicsComponent(GameObject obj) {
		this.object = obj;
	}

	@Override
    public void update(GameObject obj) {
	    obj.x += speed;
    }
}
