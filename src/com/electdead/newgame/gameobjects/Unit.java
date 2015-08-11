package com.electdead.newgame.gameobjects;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.gameobjects.components.UnitPhysicsModel;
import com.electdead.newgame.main.MainApp;

public class Unit extends GameObject {
	public UnitPhysicsModel physModel;
	public UnitState state;
	public int currHp;
	public int damage;
	public int armor;
	public double currentSpeed;
	public int velocityX;
	public int velocityY;
	
	public Rectangle2D.Double hitBox;
	public Unit target;
	public int attackTimer;
	public Rectangle2D.Double attackBox;
	
	public Unit(String name, TypeObject type, double x, double y) {
		super(name, type, x, y);
		init();
	}
	
	private void init() {
		HashMap<String, Object> props = Assets.getProperties(name);
		physModel = (UnitPhysicsModel) props.get("physicsModel");

		hitBox = new Rectangle2D.Double(x - physModel.getHitBoxWidth() / 2,
				y - physModel.getHitBoxHeight(), physModel.getHitBoxWidth(), physModel.getHitBoxHeight());

		state			= UnitState.MOVE;
		currHp			= physModel.getMaxHp();
		damage			= physModel.getDamage();
		armor 			= physModel.getArmor();
		currentSpeed	= physModel.getDefaultSpeed();
		velocityX		= physModel.getVelocityX();
		velocityY		= physModel.getVelocityY();
		attackTimer		= 0;

		if (physModel.getVelocityX() > 0) {
			attackBox = new Rectangle2D.Double(
					x, y - hitBox.height,
					physModel.getAttackRange(), hitBox.height);			
		} else {
			attackBox = new Rectangle2D.Double(
					x - physModel.getAttackRange(), y - hitBox.height,
					physModel.getAttackRange(), hitBox.height);			
		}
	}
	
	public boolean isAlive() {
		return currHp > 0;
	}
	
	public void takeDamage(int damage) {
		int total = damage - armor;
		if (total <= 0) total = 1;
		currHp -= total;
		if (currHp <= 0) {
			delete = true;
		}
	}
	
	public boolean checkDelete() {
		if ((x + hitBox.width / 2 < -300) ||
			(x - hitBox.width / 2) > MainApp.WIDTH + 300) {
			delete = true;			
		}
		return delete;
	}
	
	@Override
	public void update() {
		super.update();
	}
}
