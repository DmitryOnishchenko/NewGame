package com.electdead.newgame.gameobjectV2.ai;

import com.electdead.newgame.engine.EngineV1Old;
import com.electdead.newgame.physics.Vector2F;

public class MoveAIComponent extends AIComponent {
    private int delayTimer = 0;
    private int moveDirCorrectionTrigger = 250 / EngineV1Old.MS_PER_UPDATE;

    public MoveAIComponent(AIContainer aic, int priority) {
        super(aic, priority);
    }

    @Override
    public void think() {
        if (aic.gameObject.target == null) {
            aic.gameObject.currentState.moveDir = aic.gameObject.pModel.getMoveDir();
        } else if (delayTimer++ > moveDirCorrectionTrigger) {
            Vector2F newDir = aic.gameObject.target.currentState.pos.copy();
            newDir.sub(aic.gameObject.currentState.pos);
            newDir.normalize();
            aic.gameObject.currentState.moveDir = newDir;
        }
        aic.setMaxPriorityComponent(this);
    }

//    @Override
//    public void update(UnitOld unit) {
//        unit.moveDir = unit.pModel.getMoveDir();
//    }
}
