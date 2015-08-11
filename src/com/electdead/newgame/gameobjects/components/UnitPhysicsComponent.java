package com.electdead.newgame.gameobjects.components;

import com.electdead.newgame.gameobjects.Unit;

public class UnitPhysicsComponent implements PhysicsComponent {
	private Unit unit;
	
	public UnitPhysicsComponent(Unit obj) {
		this.unit = obj;
	}

	@Override
    public void update() {
		move();
    }
	
	public void move() {
		double shiftX 		= unit.currentSpeed * unit.velocityX;		
		unit.x 				+= shiftX;
		unit.hitBox.x 		+= shiftX;
		unit.attackBox.x 	+= shiftX;
		
		double shiftY 		= unit.currentSpeed * unit.velocityY;
		unit.y 				+= shiftY;
		unit.hitBox.y 		+= shiftY;
		unit.attackBox.y 	+= shiftY;
	}
}
