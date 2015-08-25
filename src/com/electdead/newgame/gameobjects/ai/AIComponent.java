package com.electdead.newgame.gameobjects.ai;

import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.gameobjects.actions.Action;

public abstract class AIComponent implements Comparable<AIComponent> {
	public AIContainer aic;
	public final int priority;
	public Action action;
	
	public AIComponent(AIContainer aic, int priority, Action action) {
		this.aic = aic;
		this.priority = priority;
		this.action = action;
	}
	
	@Override
	public int compareTo(AIComponent other) {
		return priority - other.priority;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
	
	public Action getAction() {
		return action;
	}
	
	public abstract void think(Unit unit);
	public abstract void update(Unit unit);
}
