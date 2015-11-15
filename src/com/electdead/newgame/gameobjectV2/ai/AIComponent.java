package com.electdead.newgame.gameobjectV2.ai;

import com.electdead.newgame.gameobject.unit.actions.Action;

public abstract class AIComponent implements Comparable<AIComponent> {
	public AIContainer aic;
	public final int priority;
	public Action action;

	public AIComponent(AIContainer aic, int priority) {
		this.aic = aic;
		this.priority = priority;
	}
	
	public void setAction(Action action) {
		this.action = action;
	}

	@Override
	public int compareTo(AIComponent other) {
		return priority - other.priority;
	}

	public Action getAction() {
		return action;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

	public abstract void think();
}
