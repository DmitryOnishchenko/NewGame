package com.electdead.newgame.gameobjects;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.gameobjects.actions.Action;
import com.electdead.newgame.gameobjects.ai.AIContainer;
import com.electdead.newgame.gameobjects.components.UnitPhysicsModel;
import com.electdead.newgame.gamestate.DevGameState;
import com.electdead.newgame.main.MainApp;
import com.electdead.newgame.physics.Vector2F;

public class Unit extends GameObject {
	private AIContainer aiContainer;
	public UnitPhysicsModel physModel;
	
	public Action currentAction;
	
	public int currHp;
	public int damage;
	public int armor;
	public float currentSpeed;
	public Vector2F dir;
	
	public Rectangle2D.Double hitBox;
	public Unit target;
	public int attackTimer;
	public Rectangle2D.Double attackBox;
	
	public Unit(String name, TypeObject type, float x, float y) {
		super(name, type, x, y);
		init();
	}
	
	private void init() {
		HashMap<String, Object> props = Assets.getProperties(name);
		physModel = (UnitPhysicsModel) props.get("physicsModel");

		hitBox = new Rectangle2D.Double(pos.x - physModel.getHitBoxWidth() / 2,
				pos.y - physModel.getHitBoxHeight(), physModel.getHitBoxWidth(), physModel.getHitBoxHeight());
		
		currHp			= physModel.getMaxHp();
		damage			= physModel.getDamage();
		armor 			= physModel.getArmor();
		currentSpeed	= physModel.getDefaultSpeed();
		dir				= physModel.getDir();
		attackTimer		= 0;

		if (dir.x > 0) {
			attackBox = new Rectangle2D.Double(
					pos.x, pos.y - hitBox.height,
					physModel.getAttackRange(), hitBox.height);
		} else {
			attackBox = new Rectangle2D.Double(
					pos.x - physModel.getAttackRange(), pos.y - hitBox.height,
					physModel.getAttackRange(), hitBox.height);			
		}
	}
	
	public void setAIContainer(AIContainer aic) {
		this.aiContainer = aic;
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
			DevGameState.floorG2.drawImage(randomBlood(), x(), (int) (pos.y - hitBox.height / 2), null);
		}
	}
	
	public boolean checkDelete() {
		if ((pos.x + hitBox.width / 2 < -300) ||
			(pos.x - hitBox.width / 2) > MainApp.WIDTH + 300) {
			delete = true;
		}
		return delete;
	}
	
	public BufferedImage randomBlood() {
		int index = (int) (Math.random() * 7);
		return DevGameState.bloodSprites.get(index);
	}
	
	@Override
	public void update() {
		aiContainer.update(this);
		super.update();
	}
}
