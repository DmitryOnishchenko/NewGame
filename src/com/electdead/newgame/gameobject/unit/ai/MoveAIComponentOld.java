package com.electdead.newgame.gameobject.unit.ai;

import com.electdead.newgame.engine.EngineV1Old;
import com.electdead.newgame.gameobject.unit.UnitOld;
import com.electdead.newgame.physics.Vector2F;

public class MoveAIComponentOld extends AIComponentOld {
    private int delayTimer = 0;
    private int moveDirCorrectionTrigger = 250 / EngineV1Old.MS_PER_UPDATE;

    public MoveAIComponentOld(AIContainerOld aic, int priority) {
        super(aic, priority);
    }

    @Override
    public void think(UnitOld unit) {
        if (unit.target == null) {
            unit.moveDir = unit.physModel.getMoveDir();
        } else if (delayTimer++ > moveDirCorrectionTrigger) {
            Vector2F newDir = unit.target.pos.copy();
            newDir.sub(unit.pos);
            newDir.normalize();
            unit.moveDir = newDir;
        }
        aic.setMaxPriorityComponent(this);
    }

//    @Override
//    public void update(UnitOld object) {
//        object.moveDir = object.pModel.getMoveDir();
//    }
}
