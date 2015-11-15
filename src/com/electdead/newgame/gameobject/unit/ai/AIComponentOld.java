package com.electdead.newgame.gameobject.unit.ai;

import com.electdead.newgame.gameobject.unit.UnitOld;
import com.electdead.newgame.gameobject.unit.actions.Action;

public abstract class AIComponentOld implements Comparable<AIComponentOld> {
	public AIContainerOld aic;
	public final int priority;
	public Action action;

	public AIComponentOld(AIContainerOld aic, int priority) {
		this.aic = aic;
		this.priority = priority;
	}
	
	public void setAction(Action action) {
		this.action = action;
	}

	@Override
	public int compareTo(AIComponentOld other) {
		return priority - other.priority;
	}

	public Action getAction() {
		return action;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

	public abstract void think(UnitOld unit);

//	public abstract void update(UnitOld unit);
}
