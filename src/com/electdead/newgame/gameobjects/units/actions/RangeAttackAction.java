package com.electdead.newgame.gameobjects.units.actions;

import com.electdead.newgame.gameobjects.TypeObject;
import com.electdead.newgame.gameobjects.projectiles.Projectile;
import com.electdead.newgame.gameobjects.units.Unit;
import com.electdead.newgame.gameobjects.units.ai.AIComponent;
import com.electdead.newgame.gamestate.DevGameState;
import com.electdead.newgame.physics.Vector2F;

public class RangeAttackAction extends Action {
	private int attackTimer = 0;
	
	public RangeAttackAction(AIComponent aiComponent, Unit unit, boolean needFullAnimation) {
		super(aiComponent, unit, needFullAnimation);
	}

	@Override
	public void execute() {
		if (unit.target != null && !unit.target.isAlive()) {
			unit.target = null;
			wait = true;
		}
		
		if (!wait && attackTimer++ > unit.physModel.getAttackSpeed()) {
			attackTimer = 0;
			spawnProjectile(unit);
			unit.target.takeDamage(unit.damage);
		}
	}
	
	@Override
	public void animationFinished() {
		wait = false;
		aiComponent.aic.unlock();
	}
	
	public void spawnProjectile(Unit unit) {
		Projectile arrow = new Projectile("woodenArrow", TypeObject.Projectile, unit.x(), unit.y());
		
		Vector2F newDir = unit.target.pos.copy();
		newDir.sub(unit.pos);
		newDir.normalize();
		arrow.dir = newDir;
		
		DevGameState.gameObjects.add(arrow);
	}
}