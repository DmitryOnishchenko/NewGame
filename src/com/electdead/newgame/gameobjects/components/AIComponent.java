package com.electdead.newgame.gameobjects.components;

import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.gameobjects.ai.AIContainer;

public abstract class AIComponent implements Comparable<AIComponent> {
	public final int priority;
	public AIContainer aic;
	
	public AIComponent(AIContainer aic, int priority) {
		this.aic = aic;
		this.priority = priority;
	}
	
	@Override
	public int compareTo(AIComponent other) {
		return priority - other.priority;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
	
	public abstract void update(Unit unit);
}
