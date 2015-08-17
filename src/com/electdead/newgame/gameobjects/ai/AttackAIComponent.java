package com.electdead.newgame.gameobjects.ai;

import com.electdead.newgame.gameobjects.Unit;

public class AttackAIComponent extends AIComponent {

	public AttackAIComponent(AIContainer aic, int priority) {
		super(aic, priority, true);
	}
	
	@Override
	public void think(Unit unit) {
		if (unit.target != null) {
			if (unit.attackBox.intersects(unit.target.hitBox)) {
				aic.setMaxPriorityComponent(this);
				unit.velocityX = 0;
			}
		}
	}
	
	@Override
	public void update(Unit unit) {
		attackEnemy(unit);
	}

	private void attackEnemy(Unit unit) {
		if (unit.attackTimer++ > unit.physModel.getAttackSpeed()) {
			unit.attackTimer = 0;
			unit.target.takeDamage(unit.damage);
//			System.out.println(
//					unit.name + "[" + unit.currHp + "/" + unit.physModel.getMaxHp() + "]"
//					+ " attack " + 
//					unit.target.name + "[" + unit.target.currHp + "/" + unit.target.physModel.getMaxHp() + "]" +
//					" with " + unit.damage);
			if (!unit.target.isAlive()) {
				unit.target = null;
				aic.maxPriorityComponent = aic.aiComponents[2];
			}
		}
	}
}
