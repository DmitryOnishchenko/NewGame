package com.electdead.newgame.gameobjects.units.actions;

import com.electdead.newgame.gameobjects.units.Unit;
import com.electdead.newgame.gameobjects.units.ai.AIComponent;

public class MeleeAttackAction extends Action {
    private int attackTimer = 0;

    public MeleeAttackAction(AIComponent aiComponent, Unit unit, boolean needFullAnimation) {
        super(aiComponent, unit, needFullAnimation);
    }

    @Override
    public void execute() {
        if (unit.target != null && !unit.target.isAlive()) {
            unit.target = null;
            wait = true;
        }

        if (!wait && attackTimer++ > unit.physModel.getAttackSpeed()) {
            checkAnimationDir();
            attackTimer = 0;
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
