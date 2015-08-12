package com.electdead.newgame.gameobjects.ai;

import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.gameobjects.components.AIComponent;

public class MoveAIComponent extends AIComponent {

	public MoveAIComponent(AIContainer aic, int priority) {
		super(aic, priority);
	}

	@Override
	public void update(Unit unit) {
		if (unit.actions.peek() == this) {
			unit.velocityX = unit.physModel.getVelocityX();
		}
		
//		if (unit.readyToAction && unit.target == null) {
//			unit.velocityX = unit.physModel.getVelocityX();
//		}
//		else {
//			unit.velocityX = 0;
//		}
	}
}
