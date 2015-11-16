package com.electdead.newgame.gameobjectV2.ai;

import com.electdead.newgame.engine.EngineV2;
import com.electdead.newgame.physics.Vector2F;

public class MoveAiComponent extends AiComponent {
    private int delayTimer = 0;
    private int moveDirCorrectionTrigger = 250 / EngineV2.MS_PER_UPDATE;

    public MoveAiComponent(AiContainer aic, int priority) {
        super(aic, priority);
    }

    @Override
    public boolean think() {
        // if target is null - move forward
        if (object.target == null) {
            object.currentState.moveDir = object.pModel.getMoveDir();
        }
        // correct the direction by trigger
        else if (delayTimer++ > moveDirCorrectionTrigger) {
            Vector2F newDir = object.target.currentState.pos.copy();
            newDir.sub(object.currentState.pos);
            newDir.normalize();
            object.currentState.moveDir = newDir;
        }

        return true;
    }
}
