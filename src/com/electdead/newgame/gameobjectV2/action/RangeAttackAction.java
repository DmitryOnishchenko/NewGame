package com.electdead.newgame.gameobjectV2.action;

import com.electdead.newgame.gameobject.GameObjectType;
import com.electdead.newgame.gameobject.projectile.ProjectileOld;
import com.electdead.newgame.gameobjectV2.BasicGameObject;
import com.electdead.newgame.gameobjectV2.ai.AiComponent;
import com.electdead.newgame.gamestate.battle.BattleState;
import com.electdead.newgame.physics.Vector2F;

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
//            object.target.takeDamage(object.currentState.damage);
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
//        ProjectileOld arrow = new ProjectileOld("woodenArrow", object.side, object.x(), object.y());
        ProjectileOld arrow = new ProjectileOld("woodenArrow", gameObject.side, GameObjectType.PROJECTILE,
                gameObject.currentState.damage, gameObject.x(), gameObject.y(), gameObject.target);

        Vector2F newDir = gameObject.target.currentState.pos.copy();
        newDir.sub(gameObject.currentState.pos);
        newDir.normalize();
        arrow.moveDir = newDir;

        BattleState.addObject(arrow);
    }
}
