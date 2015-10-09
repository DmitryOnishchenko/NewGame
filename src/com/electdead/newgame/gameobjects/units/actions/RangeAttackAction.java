package com.electdead.newgame.gameobjects.units.actions;

import com.electdead.newgame.gameobjects.GameObjectType;
import com.electdead.newgame.gameobjects.projectiles.Projectile;
import com.electdead.newgame.gameobjects.units.Unit;
import com.electdead.newgame.gameobjects.units.ai.AIComponent;
import com.electdead.newgame.gamestate.DevGameState;
import com.electdead.newgame.physics.Vector2F;

public class RangeAttackAction extends Action {
    private int attackTimer = 0;

    public RangeAttackAction(AIComponent aiComponent, Unit unit, boolean needFullAnimation) {
        super(aiComponent, unit, needFullAnimation);
    }

    @Override
    public void execute() {
        if (unit.target == null || !unit.target.isAlive()) {
            unit.target = null;
            wait = true;
        }

//        if (unit.target != null && !unit.target.isAlive()) {
//            unit.target = null;
//            wait = true;
//        }

        if (!wait && attackTimer++ > unit.physModel.getAttackSpeed()) {
            checkAnimationDir();
            attackTimer = 0;
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
                unit.damage, unit.x(), unit.y());

        Vector2F newDir = unit.target.pos.copy();
        newDir.sub(unit.pos);
        newDir.normalize();
        arrow.moveDir = newDir;

        DevGameState.grid.add(arrow);
    }
}
