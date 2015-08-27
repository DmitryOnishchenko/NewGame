package com.electdead.newgame.gameobjects.ai;

import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.gameobjects.actions.Action;
import com.electdead.newgame.gameobjects.actions.AttackAction;
import com.electdead.newgame.gameobjects.actions.MoveAction;
import com.electdead.newgame.graphics.Animation;

public class AIContainer {
	public boolean locked;
	public Unit unit;
	public AIComponent maxPriorityComponent;
	public AIComponent[] aiComponents;

	public AIContainer(Unit unit) {
		this.unit = unit;

		/* AI components */
		aiComponents = new AIComponent[3];
		
		/* Attack */
		AIComponent attackAIComponent = new AttackAIComponent(this, 2);
		Action attackAction = new AttackAction(attackAIComponent, unit, true);
		Animation attackAnimation = new Animation(attackAction, unit.graphModel.getFightSprites());
		attackAction.setAnimation(attackAnimation);
		attackAIComponent.setAction(attackAction);
		aiComponents[0] = attackAIComponent;
		
		/* Find enemy */
		aiComponents[1] = new FindEnemyAIComponent(this, 0);
		
		/* Move */
		AIComponent moveAIComponent = new MoveAIComponent(this, 4);
		Action moveAction = new MoveAction(moveAIComponent, unit, false);
		Animation moveAnimation = new Animation(moveAction, unit.graphModel.getMoveSprites());
		moveAction.setAnimation(moveAnimation);
		moveAIComponent.setAction(moveAction);
		aiComponents[2] = moveAIComponent;

		/* Set last ai component (lowest priority) */
		maxPriorityComponent = aiComponents[2];
		unit.action = maxPriorityComponent.getAction();
		locked = false;
	}

	public void update(Unit unit) {
		if (!locked) {
			if (maxPriorityComponent == null) {
				maxPriorityComponent = aiComponents[2];				
			}
			for (AIComponent ai : aiComponents) {
				if (ai.priority <= maxPriorityComponent.priority) {
					ai.think(unit);
				}
			}
			unit.action = maxPriorityComponent.getAction();
		}
	}
	
	public void setMaxPriorityComponent(AIComponent ai) {
		if (ai.priority <= maxPriorityComponent.priority) {
			maxPriorityComponent = ai;
			locked = maxPriorityComponent.getAction().needFullAnimation;
		}
	}
	
	public void unlock() {
		maxPriorityComponent = null;
		locked = false;
	}
}
