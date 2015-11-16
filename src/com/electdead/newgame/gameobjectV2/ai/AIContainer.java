package com.electdead.newgame.gameobjectV2.ai;

import com.electdead.newgame.gameobjectV2.Animation;
import com.electdead.newgame.gameobjectV2.BasicGameObject;
import com.electdead.newgame.gameobjectV2.action.Action;
import com.electdead.newgame.gameobjectV2.action.MeleeAttackAction;
import com.electdead.newgame.gameobjectV2.action.MoveAction;
import com.electdead.newgame.gameobjectV2.action.RangeAttackAction;

import java.awt.image.BufferedImage;

public class AiContainer {
    public boolean locked;
    public BasicGameObject object;
    public AiComponent maxPriorityComponent;
    public AiComponent[] aiComponents;

    public AiContainer(BasicGameObject object) {
        this.object = object;
        init();
    }

    //TODO dynamical ai components
    private void init() {
        /* AI components */
        aiComponents = new AiComponent[3];

		/* Find enemy [0] */
        aiComponents[0] = new SearchEnemyAIComponent(this, 0);

		/* Attack [1] */
        AiComponent attackAiComponent = new AttackAiComponent(this, 2);
        Action attackAction;
        //TODO hardcode
        if (object.name.endsWith("Archer")) {
            attackAction = new RangeAttackAction(attackAiComponent, true);
        } else {
            attackAction = new MeleeAttackAction(attackAiComponent, true);
        }
        BufferedImage[] spritesRight = object.gModel.getFightSpritesRight();
        BufferedImage[] spritesLeft = object.gModel.getFightSpritesLeft();
        Animation attackAnimation = new Animation(attackAction, spritesRight, spritesRight, spritesLeft);
        attackAction.setAnimation(attackAnimation);
        attackAiComponent.action = attackAction;
        aiComponents[1] = attackAiComponent;

		/* Move [2] */
        spritesRight = object.gModel.getMoveSpritesRight();
        spritesLeft = object.gModel.getMoveSpritesLeft();
        AiComponent moveAiComponent = new MoveAiComponent(this, 4);
        Action moveAction = new MoveAction(moveAiComponent, false);
        Animation moveAnimation = new Animation(moveAction, spritesRight, spritesRight, spritesLeft);
        moveAction.setAnimation(moveAnimation);
        moveAiComponent.action = moveAction;
        aiComponents[2] = moveAiComponent;

		/* Set last ai component (lowest priority) */
        maxPriorityComponent = aiComponents[2];
        object.action = maxPriorityComponent.action;
    }

    public void update() {
        // if not locked by action
        if (!locked) {
            // hardcode maxPriorityComponent
            maxPriorityComponent = aiComponents[2];

            for (AiComponent comp : aiComponents) {
                // think
                if (comp.priority <= maxPriorityComponent.priority && comp.think()) {
                    maxPriorityComponent = comp;
                }
                // update actionDelay
                if (comp.action != null) {
                    // set action to object
                    object.action = maxPriorityComponent.action;
                    // set locked mode
                    locked = maxPriorityComponent.action.needFullAnimation;
                }
            }
        }

        for (AiComponent comp : aiComponents) {
            if (comp.action != null) {
                comp.action.actionDelay++;
            }
        }
    }

    public void setLock(boolean lock) {
        this.locked = lock;
    }
}
