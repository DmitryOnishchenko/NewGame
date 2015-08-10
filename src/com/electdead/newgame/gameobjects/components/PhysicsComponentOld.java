package com.electdead.newgame.gameobjects.components;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.gameobjects.GameObject;
import com.electdead.newgame.gameobjects.GameObjectState;
import com.electdead.newgame.gameobjects.Race;
import com.electdead.newgame.gamestate.DevGameState;
import com.electdead.newgame.main.MainApp;

public class PhysicsComponentOld {
	private GameObject obj;
	private int currHp;
	private int damage;
	private int armor;
	private double currentSpeed;
	private int velocityX;
	private int velocityY;
	private PhysicsModel physModel;
	
	private Rectangle2D.Double hitBox;
	private GameObject target;
	private int attackTimer;
	private Rectangle2D.Double attackBox;
	
	public PhysicsComponentOld(GameObject obj) {
		this.obj = obj;
		init();
	}
	
	private void init() {
		HashMap<String, Object> props = Assets.getProperties(obj.name);
		physModel = (PhysicsModel) props.get("physModel");

		hitBox = new Rectangle2D.Double(obj.x - physModel.getHitBoxWidth() / 2,
				obj.y - physModel.getHitBoxHeight(), physModel.getHitBoxWidth(), physModel.getHitBoxHeight());

		currHp			= physModel.getMaxHp();
		damage			= physModel.getDamage();
		armor 			= physModel.getArmor();
		currentSpeed	= physModel.getDefaultSpeed();
		velocityX		= physModel.getVelocityX();
		velocityY		= physModel.getVelocityY();
		attackTimer		= 0;

		if (physModel.getVelocityX() > 0) {
			attackBox = new Rectangle2D.Double(
					obj.x, obj.y - hitBox.height,
					physModel.getAttackRange(), hitBox.height);			
		} else {
			attackBox = new Rectangle2D.Double(
					obj.x - physModel.getAttackRange(), obj.y - hitBox.height,
					physModel.getAttackRange(), hitBox.height);			
		}
	}

	public void update() {
		move();
		findEnemy();
		attack();
		checkDelete();
	}
	
	public void move() {
//		if (state == GameObjectState.MOVE) {
//			double shiftX = currentSpeed * velocityX;		
//			obj.x 		+= shiftX;
//			hitBox.x 	+= shiftX;
//			attackBox.x += shiftX;
//			
//			double shiftY = currentSpeed * velocityY;
//			obj.y 		+= shiftY;
//			hitBox.y 	+= shiftY;
//			attackBox.y += shiftY;			
//		}
	}
	
	public void findEnemy() {
//		if (state != GameObjectState.FIGHT) {
//			for (GameObject enemy : DevGameState.gameObjects) {
//				if (physModel.getRace() != enemy.getPhys().getRace() &&
//					attackBox.intersects(enemy.getPhys().hitBox)) {
//					target = enemy;
//					state = GameObjectState.FIGHT;
////					velocityX = 0;
//				}
//			}
//		}
	}
	
	public void attack() {
//		if (state == GameObjectState.FIGHT) {
//			if (++attackTimer > physModel.getUpdatesPerAttack()) {
//				attackTimer = 0;
//				target.getPhys().takeDamage(damage);
//				if (!target.getPhys().isAlive()) {
//					target = null;
//					state = GameObjectState.MOVE;
//				}		 
//			}
//		}
		
		
//		if (target == null) {
//			for (GameObject enemy : DemoGameState.gameObjects) {
//				if (physModel.getRace() != enemy.getPhys().getRace() &&
//					attackBox.intersects(enemy.getPhys().hitBox)) {
//					target = enemy;
//					state = GameObjectState.FIGHT;
////					velocityX = 0;
//				}
//			}			
//		} else {
//			if (++attackTimer > physModel.getUpdatesPerAttack()) {
//				attackTimer = 0;
//				target.getPhys().takeDamage(damage);
//				if (!target.getPhys().isAlive()) {
//					target = null;
//					state = GameObjectState.MOVE;
////					currentSpeed = physModel.getDefaultSpeed();
////					velocityX = physModel.getVelocityX();
//				}		 
//			}
//		}
	}
	
	public void checkDelete() {
		if ((obj.x + hitBox.width / 2 < -300) ||
			(obj.x - hitBox.width / 2) > MainApp.WIDTH + 300) {
			obj.delete = true;			
		}
		
	}
	
	public void takeDamage(int damage) {
		int total = damage - armor;
		if (total <= 0) total = 1;
		currHp -= total;
		if (currHp <= 0) {
			obj.delete = true;
		}
	}
	
	public boolean isAlive() {
		return currHp > 0;
	}
	
	public Race getRace() {
		return physModel.getRace();
	}
	
	public void setState(GameObjectState state) {
//		this.state = state;
	}
}
