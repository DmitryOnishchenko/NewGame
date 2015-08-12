package com.electdead.newgame.gameobjects.ai;

import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.gameobjects.components.AIComponent;

public class AIContainer {
	public AIComponent maxPriorityComponent;
	public AIComponent[] aiComponents;
	
	public AIContainer() {
		aiComponents = new AIComponent[3];
		aiComponents[0] = new MoveAIComponent(this, 4);
		aiComponents[1] = new FindEnemyAIComponent(this, 4);
		aiComponents[2] = new AttackAIComponent(this, 2);
		
		maxPriorityComponent = aiComponents[0];
	}
	
	public void update(Unit unit) {
		for (AIComponent ai : aiComponents) {
			ai.update(unit);
		}
	}
}
