package com.electdead.newgame.gameobjects.units.ai;

import com.electdead.newgame.engine.Cell;
import com.electdead.newgame.engine.EngineV1;
import com.electdead.newgame.gameobjects.units.Race;
import com.electdead.newgame.gameobjects.units.Unit;
import com.electdead.newgame.gamestate.DevGameState;
import com.electdead.newgame.physics.Vector2F;

import java.util.List;

public class SearchEnemyAIComponent extends AIComponent {
    private int delayTimer = 0;
    private int repeatFindTrigger = 1000 / EngineV1.MS_PER_UPDATE;
    public SearchEnemyAIComponent(AIContainer aic, int priority) {
        super(aic, priority);
    }

    @Override
    public void think(Unit unit) {
        if (unit.target == null) {
            searchTarget(unit);
        } else if (delayTimer++ > repeatFindTrigger) {
            delayTimer = 0;
            searchTarget(unit);
        }
    }

    @Override
    public void update(Unit unit) {}

    private void searchTarget(Unit unit) {
        double minLength = Double.MAX_VALUE;
        Vector2F newDir = null;
        Unit newTarget = null;
        List<Cell> cells = DevGameState.grid.getCellIfIntersectsWith(unit.searchCircle);

        for (Cell cell : cells) {
            List<Unit> list;
            if (unit.physModel.getRace() == Race.Human) {
                list = cell.getRightUnit();
            } else list = cell.getLeftUnits();

            for (Unit target : list) {
                if (target.isAlive() && intersects(unit, target)) {

                    newDir = target.pos.copy();
                    newDir.sub(unit.pos);
                    double length = newDir.length();
                    if (length < minLength) {
                        minLength = length;
                        newTarget = target;
                    }
                }
            }
        }

        if (newTarget != null) {
            unit.target = newTarget;
            newDir.normalize();
            unit.moveDir = newDir;
        } else {
            unit.moveDir = unit.physModel.getMoveDir();
        }
    }

    public boolean intersects(Unit unit, Unit enemy) {
        double unitSearchRange = unit.physModel.getSearchRange();
        double enemyHitBoxRadius = enemy.hitBox.width / 2;
        double distance = Vector2F.getDistanceOnScreen(unit.pos, enemy.pos);

        return distance < (unitSearchRange + enemyHitBoxRadius);
    }
}
