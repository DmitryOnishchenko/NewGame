package com.electdead.newgame.gameobjectV2.ai;

import com.electdead.newgame.gameobjectV2.BasicGameObject;
import com.electdead.newgame.physics.Vector2F;

public class AttackAIComponent extends AIComponent {

    public AttackAIComponent(AIContainer aic, int priority) {
        super(aic, priority);
    }

    @Override
    public void think() {
        if (aic.gameObject.target != null) {
            if (intersects(aic.gameObject, aic.gameObject.target)) {
                aic.setMaxPriorityComponent(this);
                aic.gameObject.currentState.moveDir = new Vector2F();
                action.checkAnimationDir();
            }
        }
    }

//    @Override
//    public void update(UnitOld unit) {}

    public boolean intersects(BasicGameObject gameObject, BasicGameObject enemy) {
        if (!enemy.currentState.isAlive()) {
            return false;
        }
        double unitAttackRange = gameObject.pModel.getAttackRange();
        double enemyHitBoxRadius = enemy.hitBox.width / 2;
        double distance = Vector2F.getDistanceOnScreen(gameObject.currentState.pos, enemy.currentState.pos);

        return distance < (unitAttackRange + enemyHitBoxRadius);
    }
}
