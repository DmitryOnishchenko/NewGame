package com.electdead.newgame.gameobjectV2.ai;

import com.electdead.newgame.engine.Cell;
import com.electdead.newgame.gameobject.Side;
import com.electdead.newgame.gameobjectV2.BasicGameObject;
import com.electdead.newgame.gamestate.battle.BattleState;
import com.electdead.newgame.physics.Vector2F;

import java.util.List;

public class SearchEnemyTask implements Runnable {
    private BasicGameObject object;

    public SearchEnemyTask(BasicGameObject object) {
        this.object = object;
    }

    @Override
    public void run() {
        searchTarget();
    }

    private void searchTarget() {
        double minLength = Double.MAX_VALUE;
        Vector2F newDir = null;
        BasicGameObject newTarget = null;
        List<Cell> cells = BattleState.grid.getCellIfIntersectsWith(object.searchCircle);

        for (Cell cell : cells) {
            List<BasicGameObject> targets;
            if (object.side == Side.LEFT_ARMY) {
                targets = cell.getRightUnits();
            } else {
                targets = cell.getLeftUnits();
            }

            for (BasicGameObject target : targets) {
                if (target.currentState.isAlive() && intersects(object, target)) {
                    newDir = target.currentState.pos.copy();
                    newDir.sub(object.currentState.pos);
                    double length = newDir.length();

                    if (length <= minLength) {
                        minLength = length;
                        newTarget = target;
                    }
                }
            }
        }

        if (newTarget != null) {
            object.target = newTarget;
            newDir.normalize();
            object.currentState.moveDir = newDir;
        } else {
            object.target = null;
            object.currentState.moveDir = object.pModel.getMoveDir();
        }
    }

    public boolean intersects(BasicGameObject gameObject, BasicGameObject other) {
        double unitSearchRange = gameObject.pModel.getSearchRange();
        double enemyHitBoxRadius = other.hitBox.width / 2;
        double distance = Vector2F.getDistanceOnScreen(gameObject.currentState.pos, other.currentState.pos);

        return distance < (unitSearchRange + enemyHitBoxRadius);
    }
}
