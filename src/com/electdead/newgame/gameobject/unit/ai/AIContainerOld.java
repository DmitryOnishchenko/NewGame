package com.electdead.newgame.gameobject.unit.ai;

import com.electdead.newgame.gameobject.unit.UnitOld;
import com.electdead.newgame.gameobject.unit.actions.Action;
import com.electdead.newgame.gameobject.unit.actions.MeleeAttackAction;
import com.electdead.newgame.gameobject.unit.actions.MoveAction;
import com.electdead.newgame.graphics.AnimationOld;

import java.awt.image.BufferedImage;

public class AIContainerOld {
    public boolean locked;
    public UnitOld unit;
    public AIComponentOld maxPriorityComponent;
    public AIComponentOld[] aiComponents;

    public AIContainerOld(UnitOld unit) {
        this.unit = unit;
        init();
    }

    //TODO dynamical ai components
    private void init() {
        /* AI components */
        aiComponents = new AIComponentOld[3];

		/* Find enemy [0] */
        aiComponents[0] = new SearchEnemyAIComponentOld(this, 0);

		/* Attack [1] */
        AIComponentOld attackAIComponent = new AttackAIComponentOld(this, 2);
        Action attackAction = null;
        if (unit.name.endsWith("Archer")) {
//            attackAction = new RangeAttackAction(attackAIComponent, object, true);
        } else {
            attackAction = new MeleeAttackAction(attackAIComponent, unit, true);
        }
        BufferedImage[] spritesRight = unit.graphModel.getFightSpritesRight();
        BufferedImage[] spritesLeft = unit.graphModel.getFightSpritesLeft();
        AnimationOld attackAnimation = new AnimationOld(attackAction, spritesRight, spritesRight, spritesLeft);
        attackAction.setAnimation(attackAnimation);
        attackAIComponent.setAction(attackAction);
        aiComponents[1] = attackAIComponent;

		/* Move [2] */
        spritesRight = unit.graphModel.getMoveSpritesRight();
        spritesLeft = unit.graphModel.getMoveSpritesLeft();
        AIComponentOld moveAIComponent = new MoveAIComponentOld(this, 4);
        Action moveAction = new MoveAction(moveAIComponent, unit, false);
        AnimationOld moveAnimation = new AnimationOld(moveAction, spritesRight, spritesRight, spritesLeft);
        moveAction.setAnimation(moveAnimation);
        moveAIComponent.setAction(moveAction);
        aiComponents[2] = moveAIComponent;

		/* Set last ai component (lowest priority) */
        maxPriorityComponent = aiComponents[2];
        unit.action = maxPriorityComponent.getAction();
    }

    public void update() {
        if (!locked) {
            if (maxPriorityComponent == null) {
                maxPriorityComponent = aiComponents[2];
            }
            for (AIComponentOld ai : aiComponents) {
                if (ai.priority <= maxPriorityComponent.priority) {
                    ai.think(unit);
                }
            }
            unit.action = maxPriorityComponent.getAction();
        }

        for (AIComponentOld ai : aiComponents) {
            if (ai.action != null) {
                ai.action.actionDelay++;
            }
        }
    }

    public void setMaxPriorityComponent(AIComponentOld ai) {
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
