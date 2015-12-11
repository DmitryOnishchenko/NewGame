package com.electdead.newgame.gameobjectV2.action;

import com.electdead.newgame.gameobjectV2.BasicGameObject;
import com.electdead.newgame.gameobjectV2.ai.AiComponent;
import com.electdead.newgame.gameobjectV2.projectileV2.ProjectileV2;
import com.electdead.newgame.gamestate.battle.BattleState;

public class RangeAttackAction extends Action {
    public RangeAttackAction(AiComponent aiComponent, boolean needFullAnimation) {
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
            //TODO spawn projectile
            spawnProjectile(object);
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

    private boolean targetOnEast() {
        return object.x() < object.target.x();
    }

    private boolean targetOnWest() {
        return object.x() > object.target.x();
    }

    @Override
    public void animationFinished() {
        wait = false;
        aiComponent.container.locked = false;
    }

    public void spawnProjectile(BasicGameObject gameObject) {
        ProjectileV2 projectile = new ProjectileV2("woodenArrow", gameObject);
        BattleState.addObject(projectile);
    }
}
