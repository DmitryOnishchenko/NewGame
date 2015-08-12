package com.electdead.newgame.gameobjects.components;

import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.gameobjects.ai.AIContainer;

public abstract class AIComponent implements Comparable<AIComponent> {
	public AIContainer aic;
	public final int priority;
	public boolean needToEndAnim;
	
	public AIComponent(AIContainer aic, int priority, boolean needToEndAnim) {
		this.aic = aic;
		this.priority = priority;
		this.needToEndAnim = needToEndAnim;
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
