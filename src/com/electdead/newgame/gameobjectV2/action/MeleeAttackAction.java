package com.electdead.newgame.gameobjectV2.action;

import com.electdead.newgame.gameobjectV2.ai.AiComponent;

public class MeleeAttackAction extends Action {
    public MeleeAttackAction(AiComponent aiComponent, boolean needFullAnimation) {
        super(aiComponent, needFullAnimation);
        actionTrigger = (int) (object.pModel.getAttackSpeed());
        actionDelay = actionTrigger;
    }

    @Override
    public void execute() {
        if (object.target == null || !object.target.currentState.isAlive()) {
            object.target = null;
            wait = true;
        }

        if (!wait && actionDelay > actionTrigger) {
            actionDelay = 0;

            checkAnimationDir();
            object.target.takeDamage(object.currentState.damage);
        }
    }

    @Override
    public void checkAnimationDir() {
        if (targetOnEast() && animation.watchWest()) {
            animation.swapDir(1);
        } else if (targetOnWest() && animation.watchEast()) {
            animation.swapDir(-1);
        }
    }

    @Override
    public void animationFinished() {
        wait = false;
        aiComponent.container.locked = false;
    }

    private boolean targetOnEast() {
        return object.x() < object.target.x();
    }

    private boolean targetOnWest() {
        return object.x() > object.target.x();
    }
}
