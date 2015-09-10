package com.electdead.newgame.gameobjects.units.actions;

import com.electdead.newgame.gameobjects.units.Unit;
import com.electdead.newgame.gameobjects.units.ai.AIComponent;
import com.electdead.newgame.graphics.Animation;

public abstract class Action {
	public boolean needFullAnimation;
	public boolean wait;
	public AIComponent aiComponent;
	public Unit unit;
	public Animation animation;

	public Action(AIComponent aiComponent, Unit unit, boolean needFullAnimation) {
		this.aiComponent = aiComponent;
		this.unit = unit;
		this.needFullAnimation = needFullAnimation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public abstract void execute();
	public abstract void animationFinished();
}