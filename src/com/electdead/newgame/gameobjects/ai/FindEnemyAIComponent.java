package com.electdead.newgame.gameobjects.ai;

import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.gamestate.DevGameState;
import com.electdead.newgame.physics.Vector2F;

public class FindEnemyAIComponent extends AIComponent {
	private int delay = 0;
	
	public FindEnemyAIComponent(AIContainer aic, int priority) {
		super(aic, priority);
	}
	
	@Override
	public void think(Unit unit) {
		if (delay++ > 50) {
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
			if (unit.physModel.getRace() != enemy.physModel.getRace() &&
				unit.searchCircle.intersects(enemy.hitBox)) {
				
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
}
