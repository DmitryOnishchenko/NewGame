package com.electdead.newgame.gameobjects.ai;

import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.gameobjects.actions.Action;

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

	public abstract void think(Unit unit);

	public abstract void update(Unit unit);
}
