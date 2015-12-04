package com.electdead.newgame.gameobjectV2.ai;

import com.electdead.newgame.engine.EngineV2;

public class SearchEnemyAiComponent extends AiComponent {
    private volatile int delayTimer = 0;
    private int repeatSearchTrigger = 1000 / EngineV2.MS_PER_UPDATE;

    public SearchEnemyAiComponent(AiContainer aic, int priority) {
        super(aic, priority);
    }

    @Override
    public boolean think() {
        // if target is null - search target
        if (delayTimer % 10 == 0 && object.target == null) {
//            searchTarget();
            EngineV2.submitTask(new SearchEnemyTask(object));
        }
        // search another target by trigger
        if (delayTimer++ > repeatSearchTrigger) {
            delayTimer = 0;
//            searchTarget();
            EngineV2.submitTask(new SearchEnemyTask(object));
        }

        return false;
    }

//    private void searchTarget() {
//        double minLength = Double.MAX_VALUE;
//        Vector2F newDir = null;
//        BasicGameObject newTarget = null;
//        List<Cell> cells = BattleState.grid.getCellIfIntersectsWith(object.searchCircle);
//
//        for (Cell cell : cells) {
//            List<BasicGameObject> targets;
//            if (object.side == Side.LEFT_ARMY) {
//                targets = cell.getRightUnits();
//            } else {
//                targets = cell.getLeftUnits();
//            }
//
//            for (BasicGameObject target : targets) {
//                if (target.currentState.isAlive() && intersects(object, target)) {
//                    newDir = target.currentState.pos.copy();
//                    newDir.sub(object.currentState.pos);
//                    double length = newDir.length();
//
//                    if (length <= minLength) {
//                        minLength = length;
//                        newTarget = target;
//                    }
//                }
//            }
//        }
//
//        if (newTarget != null) {
//            object.target = newTarget;
//            newDir.normalize();
//            object.currentState.moveDir = newDir;
//        } else {
//            object.target = null;
//            object.currentState.moveDir = object.pModel.getMoveDir();
//        }
//    }

//    public boolean intersects(BasicGameObject gameObject, BasicGameObject other) {
//        double unitSearchRange = gameObject.pModel.getSearchRange();
//        double enemyHitBoxRadius = other.hitBox.width / 2;
//        double distance = Vector2F.getDistanceOnScreen(gameObject.currentState.pos, other.currentState.pos);
//
//        return distance < (unitSearchRange + enemyHitBoxRadius);
//    }
}
