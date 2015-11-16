package com.electdead.newgame.gameobjectV2.ai;

import com.electdead.newgame.gameobjectV2.BasicGameObject;
import com.electdead.newgame.physics.Vector2F;

public class AttackAiComponent extends AiComponent {

    public AttackAiComponent(AiContainer aic, int priority) {
        super(aic, priority);
    }

    @Override
    public boolean think() {
        if (object.target != null && canAttack(object)) {
            container.object.currentState.moveDir = new Vector2F();
            action.checkAnimationDir();

            return true;
        }

        return false;
    }

    /**
     * Checks if object can attack its target
     * @param object object with target
     * @return <b>true</b> - if target isAlive and distance < (attackRange + targetHitBoxRadius),
     * otherwise <b>false</b>
     */
    private boolean canAttack(BasicGameObject object) {
        BasicGameObject target = object.target;

        if (!target.currentState.isAlive()) {
            return false;
        }

        double attackRange = object.pModel.getAttackRange();
        double targetHitBoxRadius = target.hitBox.width / 2;
        double distance = Vector2F.getDistanceOnScreen(object.currentState.pos, target.currentState.pos);

        return distance < (attackRange + targetHitBoxRadius);
    }
}
