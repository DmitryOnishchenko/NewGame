package com.electdead.newgame.gameobjects.units.ai;

import com.electdead.newgame.engine.EngineV1;
import com.electdead.newgame.gameobjects.units.Unit;
import com.electdead.newgame.physics.Vector2F;

public class MoveAIComponent extends AIComponent {
    private int delayTimer = 0;
    private int moveDirCorrectionTrigger = 250 / EngineV1.MS_PER_UPDATE;

    public MoveAIComponent(AIContainer aic, int priority) {
        super(aic, priority);
    }

    @Override
    public void think(Unit unit) {
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

    @Override
    public void update(Unit unit) {
        unit.moveDir = unit.physModel.getMoveDir();
    }
}
