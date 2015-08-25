package com.electdead.newgame.gameobjects.ai;

import com.electdead.newgame.gameobjects.Unit;

public class AIContainer {
	public Unit unit;
	public AIComponent maxPriorityComponent;
	public AIComponent[] aiComponents;
	
	public AIContainer(Unit unit) {
		this.unit = unit;
		
		aiComponents = new AIComponent[3];
		aiComponents[0] = new AttackAIComponent(this, 2, null);
		aiComponents[1] = new FindEnemyAIComponent(this, 0, null);
		aiComponents[2] = new MoveAIComponent(this, 4, null);
		
		/* Last ai component (lowest priority) */
		maxPriorityComponent = aiComponents[2];
		unit.currentAction = maxPriorityComponent.getAction();
	}
	
	public void setMaxPriorityComponent(AIComponent ai) {
		if (ai.priority <= maxPriorityComponent.priority) {
			maxPriorityComponent = ai;
		}
	}
	
	public void update(Unit unit) {
		for (AIComponent ai : aiComponents) {
			if (ai.priority < maxPriorityComponent.priority) {
				ai.think(unit);
			}
		}
		maxPriorityComponent.update(unit);
	}
}
