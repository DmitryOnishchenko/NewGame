package com.electdead.newgame.gameobject.unit.ai;

import com.electdead.newgame.gameobject.unit.Unit;
import com.electdead.newgame.gameobject.unit.actions.Action;
import com.electdead.newgame.gameobject.unit.actions.MeleeAttackAction;
import com.electdead.newgame.gameobject.unit.actions.MoveAction;
import com.electdead.newgame.gameobject.unit.actions.RangeAttackAction;
import com.electdead.newgame.graphics.Animation;

import java.awt.image.BufferedImage;

public class AIContainer {
    public boolean locked;
    public Unit unit;
    public AIComponent maxPriorityComponent;
    public AIComponent[] aiComponents;

    public AIContainer(Unit unit) {
        this.unit = unit;
        init();
    }

    //TODO dynamical ai components
    private void init() {
        /* AI components */
        aiComponents = new AIComponent[3];

		/* Find enemy [0] */
        aiComponents[0] = new SearchEnemyAIComponent(this, 0);

		/* Attack [1] */
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
        aiComponents[1] = attackAIComponent;

		/* Move [2] */
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

        for (AIComponent ai : aiComponents) {
            if (ai.action != null) {
                ai.action.actionDelay++;
            }
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
