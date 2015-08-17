package com.electdead.newgame.gameobjects.ai;

import com.electdead.newgame.gameobjects.Unit;

public abstract class AIComponent implements Comparable<AIComponent> {
	public AIContainer aic;
	public final int priority;
	public boolean fullAnimation;
	
	public AIComponent(AIContainer aic, int priority, boolean fullAnimation) {
		this.aic = aic;
		this.priority = priority;
		this.fullAnimation = fullAnimation;
	}
	
	@Override
	public int compareTo(AIComponent other) {
		return priority - other.priority;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
	
	public abstract void think(Unit unit);
	public abstract void update(Unit unit);
}
