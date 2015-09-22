package com.electdead.newgame.physics;

import com.electdead.newgame.gameobjects.units.Unit;

public class UnitPhysicsComponent implements PhysicsComponent {
	private Unit unit;
	
	public UnitPhysicsComponent(Unit obj) {
		this.unit = obj;
	}

	@Override
    public void update() {
//		move();
//		unit.currentAction.execute();
    }
	
	public void move() {
//		if (unit.state == UnitState.MOVE) {
			float shiftX 		= unit.currentSpeed * unit.moveDir.x;
			unit.pos.x 			+= shiftX;
			unit.hitBox.x 		+= shiftX;
			unit.attackBox.x 	+= shiftX;
			
			float shiftY 		= unit.currentSpeed * unit.moveDir.y;
			unit.pos.y 				+= shiftY;
			unit.hitBox.y 		+= shiftY;
			unit.attackBox.y 	+= shiftY;
//		}
	}
}
