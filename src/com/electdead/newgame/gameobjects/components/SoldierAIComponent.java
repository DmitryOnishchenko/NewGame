package com.electdead.newgame.gameobjects.components;

import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.gameobjects.UnitState;
import com.electdead.newgame.gamestate.DevGameState;

public class SoldierAIComponent implements AIComponent {
	private Unit unit;
	
	public SoldierAIComponent(Unit obj) {
		this.unit = obj;
	}
	
	@Override
    public void update() {
		findTarget();
		attack();
		unit.checkDelete();
    }
	
	private void findTarget() {
		if (unit.target == null) {
			for (Unit enemy : DevGameState.units) {
				if (unit.physModel.getRace() != enemy.physModel.getRace() &&
					unit.attackBox.intersects(enemy.hitBox)) {
					unit.target = enemy;
					unit.state = UnitState.FIGHT;
//					unit.nextState = UnitState.FIGHT;
				}
			}
		}
	}
	
	public void attack() {
		if (unit.target != null) {
			if (unit.attackTimer++ > unit.physModel.getAttackSpeed()) {
				unit.attackTimer = 0;
				unit.target.takeDamage(unit.damage);
			}
			if (!unit.target.isAlive()) {
				unit.target = null;
				unit.state = UnitState.MOVE;
//				unit.nextState = UnitState.MOVE;
			}
		}
	}
}
