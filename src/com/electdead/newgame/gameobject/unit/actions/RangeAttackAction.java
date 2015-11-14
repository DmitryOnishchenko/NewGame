package com.electdead.newgame.gameobject.unit.actions;

import com.electdead.newgame.gameobject.GameObjectType;
import com.electdead.newgame.gameobject.projectile.Projectile;
import com.electdead.newgame.gameobject.unit.Unit;
import com.electdead.newgame.gameobject.unit.ai.AIComponent;
import com.electdead.newgame.gamestate.DevGameState;
import com.electdead.newgame.physics.Vector2F;

public class RangeAttackAction extends Action {
    public RangeAttackAction(AIComponent aiComponent, Unit unit, boolean needFullAnimation) {
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
            //TODO spawn projectile
            spawnProjectile(unit);
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
        return unit.x() < unit.target.x();
    }

    private boolean targetOnWest() {
        return unit.x() > unit.target.x();
    }

    @Override
    public void animationFinished() {
        wait = false;
        aiComponent.aic.unlock();
    }

    public void spawnProjectile(Unit unit) {
//        Projectile arrow = new Projectile("woodenArrow", unit.side, unit.x(), unit.y());
        Projectile arrow = new Projectile("woodenArrow", unit.side, GameObjectType.PROJECTILE,
                unit.damage, unit.x(), unit.y(), unit.target);

        Vector2F newDir = unit.target.pos.copy();
        newDir.sub(unit.pos);
        newDir.normalize();
        arrow.moveDir = newDir;

        DevGameState.grid.add(arrow);
    }
}
