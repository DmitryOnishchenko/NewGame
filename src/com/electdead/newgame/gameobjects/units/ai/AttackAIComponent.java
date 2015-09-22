package com.electdead.newgame.gameobjects.units.ai;

import com.electdead.newgame.gameobjects.units.Unit;
import com.electdead.newgame.physics.Vector2F;

public class AttackAIComponent extends AIComponent {

	public AttackAIComponent(AIContainer aic, int priority) {
		super(aic, priority);
	}
	
	@Override
	public void think(Unit unit) {
		if (unit.target != null) {
			if (intersects(unit, unit.target)) {
				aic.setMaxPriorityComponent(this);
				unit.moveDir = new Vector2F();
			}
		}
	}
	
	@Override
	public void update(Unit unit) {}
	
	public boolean intersects(Unit unit, Unit enemy) {
		double unitAttackRange = unit.physModel.getAttackRange();
		double enemyHitBoxRadius = enemy.hitBox.width / 2;
		double distance = Vector2F.getDistanceOnScreen(unit.pos, enemy.pos);
		
		return distance < (unitAttackRange + enemyHitBoxRadius);
	}
}
