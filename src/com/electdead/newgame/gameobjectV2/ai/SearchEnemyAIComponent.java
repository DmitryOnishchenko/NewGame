package com.electdead.newgame.gameobjectV2.ai;

import com.electdead.newgame.engine.Cell;
import com.electdead.newgame.engine.EngineV1Old;
import com.electdead.newgame.gameobject.unit.Race;
import com.electdead.newgame.gameobject.unit.UnitOld;
import com.electdead.newgame.gameobjectV2.BasicGameObject;
import com.electdead.newgame.gamestate.DevGameState;
import com.electdead.newgame.physics.Vector2F;

import java.util.List;

public class SearchEnemyAIComponent extends AIComponent {
    private int delayTimer = 0;
    private int repeatSearchTrigger = 1000 / EngineV1Old.MS_PER_UPDATE;

    public SearchEnemyAIComponent(AIContainer aic, int priority) {
        super(aic, priority);
    }

    @Override
    public void think() {
        if (aic.gameObject.target == null) {
            searchTarget(aic.gameObject);
        } else if (delayTimer++ > repeatSearchTrigger) {
            delayTimer = 0;
            searchTarget(aic.gameObject);
        }
    }

//    @Override
//    public void update(UnitOld unit) {}

    private void searchTarget(BasicGameObject unit) {
        double minLength = Double.MAX_VALUE;
        Vector2F newDir = null;
        UnitOld newTarget = null;
        List<Cell> cells = DevGameState.grid.getCellIfIntersectsWith(unit.searchCircle);

        for (Cell cell : cells) {
            List<BasicGameObject> list;
            if (aic.gameObject.pModel.getRace() == Race.Human) {
                list = cell.getRightUnits();
            } else list = cell.getLeftUnits();

//            for (BasicGameObject target : list) {
//                if (target.currentState.isAlive() && intersects(unit, target)) {
//
//                    newDir = target.pos.copy();
//                    newDir.sub(aic.unit.currentState.pos);
//                    double length = newDir.length();
//                    if (length < minLength) {
//                        minLength = length;
//                        newTarget = (UnitOld) target;
//                    }
//                }
//            }
        }

//        if (newTarget != null) {
//            unit.target = newTarget;
//            newDir.normalize();
//            unit.moveDir = newDir;
//        } else {
//            unit.target = null;
//            unit.moveDir = unit.physModel.getMoveDir();
//        }
    }

    public boolean intersects(BasicGameObject gameObject, BasicGameObject other) {
//        UnitOld unit = (UnitOld) unit;
//        UnitOld enemy = (UnitOld) other;

        double unitSearchRange = gameObject.pModel.getSearchRange();
        double enemyHitBoxRadius = other.hitBox.width / 2;
        double distance = Vector2F.getDistanceOnScreen(gameObject.currentState.pos, other.currentState.pos);

        return distance < (unitSearchRange + enemyHitBoxRadius);
    }
}
