package com.electdead.newgame.gameobjects.ai;

import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.gameobjects.actions.Action;

public class MoveAIComponent extends AIComponent {

	public MoveAIComponent(AIContainer aic, int priority, Action action) {
		super(aic, priority, action);
	}

	@Override
	public void think(Unit unit) {
		if (unit.target == null) {
			aic.setMaxPriorityComponent(this);
		}
	}

	@Override
	public void update(Unit unit) {
		unit.dir = unit.physModel.getDir();
	}
}
