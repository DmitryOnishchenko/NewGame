package com.electdead.newgame.gameobject.unit.ai;

import com.electdead.newgame.gameobject.unit.UnitOld;
import com.electdead.newgame.physics.Vector2F;

public class AttackAIComponentOld extends AIComponentOld {

    public AttackAIComponentOld(AIContainerOld aic, int priority) {
        super(aic, priority);
    }

    @Override
    public void think(UnitOld unit) {
        if (unit.target != null) {
            if (intersects(unit, unit.target)) {
                aic.setMaxPriorityComponent(this);
                unit.moveDir = new Vector2F();
                action.checkAnimationDir();
            }
        }
    }

//    @Override
//    public void update(UnitOld unit) {}

    public boolean intersects(UnitOld unit, UnitOld enemy) {
        if (!enemy.isAlive()) {
            return false;
        }
        double unitAttackRange = unit.physModel.getAttackRange();
        double enemyHitBoxRadius = enemy.hitBox.width / 2;
        double distance = Vector2F.getDistanceOnScreen(unit.pos, enemy.pos);

        return distance < (unitAttackRange + enemyHitBoxRadius);
    }
}
