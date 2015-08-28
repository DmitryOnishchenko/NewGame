package com.electdead.newgame.gameobjects.components;

import com.electdead.newgame.engine.EngineV1;
import com.electdead.newgame.gameobjects.Race;
import com.electdead.newgame.physics.Vector2F;

public class UnitPhysicsModel {
	private Race race;
	private int maxHp;
	private int armor;
	private float defaultSpeed;
	private Vector2F dir;
	private int damage;
	private float attackSpeed;
	private float attackRange;
	private float searchRange;
	private int spawnPrice;
	private int pricePerHead;
	private float hitBoxWidth;
	private float hitBoxHeight;
	
	public UnitPhysicsModel() {}
	
	/* Getters */
	public Race getRace() { return race; }

	public int getMaxHp() {	return maxHp; }
	
	public int getArmor() {	return armor; }

	public float getDefaultSpeed() { return defaultSpeed; }

	public Vector2F getDir() { return dir; }

	public int getDamage() { return damage; }

	public float getAttackSpeed() { return attackSpeed; }

	public float getAttackRange() { return attackRange; }
	
	public float getSearchRange() { return searchRange; }
	
	public int getSpawnPrice() { return spawnPrice; }
	
	public int getPricePerHead() { return pricePerHead; }
	
	public float getHitBoxWidth() { return hitBoxWidth; }
	
	public float getHitBoxHeight() { return hitBoxHeight; }

	/* Setters */
	public void setRace(Race race) { this.race = race; }

	public void setMaxHp(int maxHp) { this.maxHp = maxHp; }
	
	public void setArmor(int armor) { this.armor = armor; }

	public void setDefaultSpeed(float defaultSpeed) {
		this.defaultSpeed = defaultSpeed / EngineV1.UPDATE_PER_SEC;
	}

	public void setDir(Vector2F dir) { this.dir = dir; }
	
	public void setDamage(int damage) { this.damage = damage; }

	public void setAttackSpeed(float attackSpeed) { 
		this.attackSpeed = attackSpeed / EngineV1.MS_PER_UPDATE;
	}

	public void setAttackRange(float attackRange) { this.attackRange = attackRange; }
	
	public void setSearchRange(float searchRange) { this.searchRange = searchRange; }
	
	public void setSpawnPrice(int spawnPrice) { this.spawnPrice = spawnPrice; }
	
	public void setPricePerHead(int pricePerHead) { this.pricePerHead = pricePerHead; }
	
	public void setHitBoxWidth(float hitBoxWidth) { this.hitBoxWidth = hitBoxWidth; }
	
	public void setHitBoxHeight(float hitBoxHeight) { this.hitBoxHeight = hitBoxHeight; }
}
