package com.electdead.newgame.gameobjects.units.ai;

import com.electdead.newgame.gameobjects.units.Unit;
import com.electdead.newgame.gamestate.DevGameState;
import com.electdead.newgame.physics.Vector2F;

public class FindEnemyAIComponent extends AIComponent {
	private int delay = 0;
	
	public FindEnemyAIComponent(AIContainer aic, int priority) {
		super(aic, priority);
	}
	
	@Override
	public void think(Unit unit) {
		if (unit.target == null) {
			findTarget(unit);
		} else if (delay++ > 100) {
			delay = 0;
			findTarget(unit);
		}
	}
	
	@Override
	public void update(Unit unit) {}

	private void findTarget(Unit unit) {
		double minLength = Double.MAX_VALUE;
		Vector2F newDir = null;
		Unit target = null;
		
		for (Unit enemy : DevGameState.units) {
			if (enemy.isAlive() &&
				unit.physModel.getRace() != enemy.physModel.getRace() &&
				intersects(unit, enemy)) {
				
				newDir = enemy.pos.copy();
				newDir.sub(unit.pos);
				double length = newDir.length();
				if (length < minLength) {
					minLength = length;
					target = enemy;
				}	
			}
		}
		
		if (target != null) {
			unit.target = target;
			newDir.normalize();
			unit.dir = newDir;			
		}
	}
	
	public boolean intersects(Unit unit, Unit enemy) {
		double unitSearchRange = unit.physModel.getSearchRange();
		double enemyHitBoxRadius = enemy.hitBox.width / 2;
		double distance = Vector2F.getDistanceOnScreen(unit.pos, enemy.pos);
		
		return distance < (unitSearchRange + enemyHitBoxRadius);
	}
}
