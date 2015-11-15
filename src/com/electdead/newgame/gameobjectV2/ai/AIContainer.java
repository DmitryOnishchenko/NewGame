package com.electdead.newgame.gameobjectV2.ai;

import com.electdead.newgame.gameobject.unit.actions.Action;
import com.electdead.newgame.gameobjectV2.BasicGameObject;

public class AIContainer {
    public boolean locked;
    public BasicGameObject gameObject;
    public AIComponent maxPriorityComponent;
    public AIComponent[] aiComponents;

    public AIContainer(BasicGameObject gameObject) {
        this.gameObject = gameObject;
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
        Action attackAction;
//        if (unit.name.endsWith("Archer")) {
//            attackAction = new RangeAttackAction(attackAIComponent, unit, true);
//        } else {
//            attackAction = new MeleeAttackAction(attackAIComponent, unit, true);
//        }
//        BufferedImage[] spritesRight = unit.gModel.getFightSpritesRight();
//        BufferedImage[] spritesLeft = unit.gModel.getFightSpritesLeft();
//        AnimationOld attackAnimation = new AnimationOld(attackAction, spritesRight, spritesRight, spritesLeft);
//        attackAction.setAnimation(attackAnimation);
//        attackAIComponent.setAction(attackAction);
        aiComponents[1] = attackAIComponent;

		/* Move [2] */
//        spritesRight = unit.gModel.getMoveSpritesRight();
//        spritesLeft = unit.gModel.getMoveSpritesLeft();
//        AIComponent moveAIComponent = new MoveAIComponent(this, 4);
//        Action moveAction = new MoveAction(moveAIComponent, unit, false);
//        AnimationOld moveAnimation = new AnimationOld(moveAction, spritesRight, spritesRight, spritesLeft);
//        moveAction.setAnimation(moveAnimation);
//        moveAIComponent.setAction(moveAction);
//        aiComponents[2] = moveAIComponent;

		/* Set last ai component (lowest priority) */
        maxPriorityComponent = aiComponents[2];
        gameObject.action = maxPriorityComponent.getAction();
    }

    public void update() {
        if (!locked) {
            if (maxPriorityComponent == null) {
                maxPriorityComponent = aiComponents[2];
            }
            for (AIComponent ai : aiComponents) {
                if (ai.priority <= maxPriorityComponent.priority) {
                    ai.think();
                }
            }
//            unit.action = maxPriorityComponent.getAction();
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
