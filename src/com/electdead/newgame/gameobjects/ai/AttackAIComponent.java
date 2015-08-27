package com.electdead.newgame.gameobjects.ai;

import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.physics.Vector2F;

public class AttackAIComponent extends AIComponent {

	public AttackAIComponent(AIContainer aic, int priority) {
		super(aic, priority);
	}
	
	@Override
	public void think(Unit unit) {
		if (unit.target != null) {
			if (unit.attackBox.intersects(unit.target.hitBox)) {
				aic.setMaxPriorityComponent(this);
				unit.dir = new Vector2F();
			}
		}
	}
	
	@Override
	public void update(Unit unit) {}
}
