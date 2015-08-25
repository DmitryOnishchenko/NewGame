package com.electdead.newgame.gameobjects.actions;

import com.electdead.newgame.gameobjects.Unit;

public abstract class Action {
	public boolean lock;
	public Unit unit;
	
	public Action(Unit unit, boolean lock) {
		this.unit = unit;
		this.lock = lock;
	}
	
//	public boolean 
	
	abstract void execute();
}
