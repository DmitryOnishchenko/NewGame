package com.electdead.newgame.gameobjects.ai;

import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.gamestate.DevGameState;
import com.electdead.newgame.physics.Vector2F;

public class FindEnemyAIComponent extends AIComponent {

	public FindEnemyAIComponent(AIContainer aic, int priority) {
		super(aic, priority);
	}
	
	@Override
	public void think(Unit unit) {
//		if (unit.target == null) {
			findTarget(unit);
//		}
	}
	
	@Override
	public void update(Unit unit) {}

	private void findTarget(Unit unit) {
		double min = Double.MAX_VALUE;
		Unit minTarget = null;
		
		for (Unit enemy : DevGameState.units) {
			if (unit.physModel.getRace() != enemy.physModel.getRace() &&
				unit.searchCircle.intersects(enemy.hitBox)) {
				
				Vector2F l = enemy.pos.copy();
				l.sub(unit.pos);
				double length = l.length();
				if (length < min) {
					min = length;
					minTarget = enemy;
				}	
			}
		}
		
		if (minTarget != null) {
			unit.target = minTarget;
			Vector2F newDir = minTarget.pos.copy();
			newDir.sub(unit.pos);
			newDir.normalize();
			unit.dir = newDir;			
		}
	}
}
