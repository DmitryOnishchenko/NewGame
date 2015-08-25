package com.electdead.newgame.gameobjects.ai;

import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.gameobjects.actions.Action;
import com.electdead.newgame.gamestate.DevGameState;

public class FindEnemyAIComponent extends AIComponent {

	public FindEnemyAIComponent(AIContainer aic, int priority, Action action) {
		super(aic, priority, action);
	}
	
	@Override
	public void think(Unit unit) {
		if (unit.target == null) {
			findTarget(unit);
		}
	}
	@Override
	public void update(Unit unit) {}

	private void findTarget(Unit unit) {
		for (Unit enemy : DevGameState.units) {
			if (unit.physModel.getRace() != enemy.physModel.getRace() &&
				unit.attackBox.intersects(enemy.hitBox)) {
				unit.target = enemy;
//				unit.velocityX = 0;
//				aic.setMaxPriorityComponent(this);
			}
		}
	}
}
