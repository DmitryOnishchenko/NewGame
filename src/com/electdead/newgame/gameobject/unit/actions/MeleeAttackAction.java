package com.electdead.newgame.gameobject.unit.actions;

import com.electdead.newgame.gameobject.unit.UnitOld;
import com.electdead.newgame.gameobject.unit.ai.AIComponentOld;

public class MeleeAttackAction extends Action {
    public MeleeAttackAction(AIComponentOld aiComponent, UnitOld unit, boolean needFullAnimation) {
        super(aiComponent, unit, needFullAnimation);
        actionTrigger = (int) (unit.physModel.getAttackSpeed());
        actionDelay = actionTrigger;
    }

    @Override
    public void execute() {
        if (unit.target == null || !unit.target.isAlive()) {
            unit.target = null;
            wait = true;
        }

        if (!wait && actionDelay > actionTrigger) {
            actionDelay = 0;

            checkAnimationDir();
            unit.target.takeDamage(unit.damage);
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
        aiComponent.aic.unlock();
    }

    private boolean targetOnEast() {
        return unit.x() < unit.target.x();
    }

    private boolean targetOnWest() {
        return unit.x() > unit.target.x();
    }
}
