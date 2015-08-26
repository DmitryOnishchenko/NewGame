package com.electdead.newgame.gameobjects.actions;

import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.gameobjects.ai.AIComponent;

public class AttackAction extends Action {

	public AttackAction(AIComponent aiComponent, Unit unit, boolean needFullAnimation) {
		super(aiComponent, unit, needFullAnimation);
	}

	@Override
	public void execute() {
		if (!wait && unit.attackTimer++ > unit.physModel.getAttackSpeed()) {
			unit.attackTimer = 0;
			unit.target.takeDamage(unit.damage);
			if (!unit.target.isAlive()) {
				unit.target = null;
				wait = true;
			}
		}
	}
	
	@Override
	public void animationFinished() {
		wait = false;
		aiComponent.aic.unlock();
	}
}
