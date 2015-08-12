package com.electdead.newgame.gameobjects.ai;

import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.gameobjects.components.AIComponent;
import com.electdead.newgame.gamestate.DevGameState;

public class FindEnemyAIComponent extends AIComponent {

	public FindEnemyAIComponent(int priority) {
		super(priority);
	}
	
	@Override
	public void update(Unit unit) {
		if (unit.target == null) {
			findTarget(unit);
		}
	}

	private void findTarget(Unit unit) {
		for (Unit enemy : DevGameState.units) {
			if (unit.physModel.getRace() != enemy.physModel.getRace() &&
				unit.attackBox.intersects(enemy.hitBox)) {
				unit.target = enemy;
			}
		}
	}
}
