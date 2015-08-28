package com.electdead.newgame.gameobjects.actions;

import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.gameobjects.ai.AIComponent;

public class AttackAction extends Action {
	private int attackTimer = 0;
	
	public AttackAction(AIComponent aiComponent, Unit unit, boolean needFullAnimation) {
		super(aiComponent, unit, needFullAnimation);
	}

	@Override
	public void execute() {
		if (unit.target != null && !unit.target.isAlive()) {
			unit.target = null;
			wait = true;
		}
		
		if (!wait && attackTimer++ > unit.physModel.getAttackSpeed()) {
			attackTimer = 0;
			unit.target.takeDamage(unit.damage);
		}
	}
	
	@Override
	public void animationFinished() {
		wait = false;
		aiComponent.aic.unlock();
	}
}
