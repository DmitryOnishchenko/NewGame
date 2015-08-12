package com.electdead.newgame.gameobjects.ai;

import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.gameobjects.components.AIComponent;

public class AttackAIComponent extends AIComponent {

	public AttackAIComponent(int priority) {
		super(priority);
	}
	
	@Override
	public void update(Unit unit) {
		if (unit.target != null) {
			attackEnemy(unit);
		}
	}

	private void attackEnemy(Unit unit) {
		if (unit.attackTimer++ > unit.physModel.getAttackSpeed()) {
			unit.readyToAction = false;
			unit.attackTimer = 0;
			unit.target.takeDamage(unit.damage);
//			System.out.println(
//					unit.name + "[" + unit.currHp + "/" + unit.physModel.getMaxHp() + "]"
//					+ " attack " + 
//					unit.target.name + "[" + unit.target.currHp + "/" + unit.target.physModel.getMaxHp() + "]" +
//					" with " + unit.damage);
			if (!unit.target.isAlive()) {
				unit.target = null;
				unit.readyToAction = true;
			}
		}
	}
}
