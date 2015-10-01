package com.electdead.newgame.gameobjects.units.ai;

import com.electdead.newgame.gameobjects.units.Unit;
import com.electdead.newgame.gameobjects.units.actions.Action;
import com.electdead.newgame.gameobjects.units.actions.MeleeAttackAction;
import com.electdead.newgame.gameobjects.units.actions.MoveAction;
import com.electdead.newgame.gameobjects.units.actions.RangeAttackAction;
import com.electdead.newgame.graphics.Animation;

import java.awt.image.BufferedImage;

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
        Action attackAction = null;
        if (unit.name.endsWith("Archer")) {
            attackAction = new RangeAttackAction(attackAIComponent, unit, true);
        } else {
            attackAction = new MeleeAttackAction(attackAIComponent, unit, true);
        }
        BufferedImage[] spritesRight = unit.graphModel.getFightSpritesRight();
        BufferedImage[] spritesLeft = unit.graphModel.getFightSpritesLeft();
        Animation attackAnimation = new Animation(attackAction, spritesRight, spritesRight, spritesLeft);
        attackAction.setAnimation(attackAnimation);
        attackAIComponent.setAction(attackAction);
        aiComponents[0] = attackAIComponent;
		
		/* Find enemy */
        aiComponents[1] = new SearchEnemyAIComponent(this, 0);
		
		/* Move */
        spritesRight = unit.graphModel.getMoveSpritesRight();
        spritesLeft = unit.graphModel.getMoveSpritesLeft();
        AIComponent moveAIComponent = new MoveAIComponent(this, 4);
        Action moveAction = new MoveAction(moveAIComponent, unit, false);
        Animation moveAnimation = new Animation(moveAction, spritesRight, spritesRight, spritesLeft);
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
