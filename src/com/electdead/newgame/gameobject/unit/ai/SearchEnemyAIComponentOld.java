package com.electdead.newgame.gameobject.unit.ai;

import com.electdead.newgame.engine.CellOld;
import com.electdead.newgame.engine.EngineV1Old;
import com.electdead.newgame.gameobject.GameObjectOld;
import com.electdead.newgame.gameobject.unit.UnitOld;
import com.electdead.newgame.gamestate.DevGameState;
import com.electdead.newgame.physics.Vector2F;

import java.util.List;

public class SearchEnemyAIComponentOld extends AIComponentOld {
    private int delayTimer = 0;
    private int repeatSearchTrigger = 1000 / EngineV1Old.MS_PER_UPDATE;

    public SearchEnemyAIComponentOld(AIContainerOld aic, int priority) {
        super(aic, priority);
    }

    @Override
    public void think(UnitOld unit) {
        if (unit.target == null) {
            searchTarget(unit);
        } else if (delayTimer++ > repeatSearchTrigger) {
            delayTimer = 0;
            searchTarget(unit);
        }
    }

//    @Override
//    public void update(UnitOld object) {}

    private void searchTarget(UnitOld unit) {
        double minLength = Double.MAX_VALUE;
        Vector2F newDir = null;
        UnitOld newTarget = null;
        List<CellOld> cells = DevGameState.grid.getCellIfIntersectsWith(unit.searchCircle);

//        for (CellOld cell : cells) {
//            List<GameObjectOld> list;
//            if (object.physModel.getRace() == Race.Human) {
//                list = cell.getRightUnits();
//            } else list = cell.getLeftUnits();
//
//            for (GameObjectOld target : list) {
//                if (((UnitOld) target).isAlive() && intersects(object, target)) {
//
//                    newDir = target.pos.copy();
//                    newDir.sub(object.pos);
//                    double length = newDir.length();
//                    if (length < minLength) {
//                        minLength = length;
//                        newTarget = (UnitOld) target;
//                    }
//                }
//            }
//        }

        if (newTarget != null) {
            unit.target = newTarget;
            newDir.normalize();
            unit.moveDir = newDir;
        } else {
            unit.target = null;
            unit.moveDir = unit.physModel.getMoveDir();
        }
    }

    public boolean intersects(GameObjectOld gameObject, GameObjectOld other) {
        UnitOld unit = (UnitOld) gameObject;
        UnitOld enemy = (UnitOld) other;

        double unitSearchRange = unit.physModel.getSearchRange();
        double enemyHitBoxRadius = enemy.hitBox.width / 2;
        double distance = Vector2F.getDistanceOnScreen(unit.pos, enemy.pos);

        return distance < (unitSearchRange + enemyHitBoxRadius);
    }
}
