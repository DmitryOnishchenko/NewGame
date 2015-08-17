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
	private float velocityX;
	private float velocityY;
	private int damage;
	private float attackSpeed;
//	private int updatesPerAttack;
	private float attackRange;
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
//	public float getVelocityX() {	return velocityX; }
//
//	public float getVelocityY() {	return velocityY; }

	public int getDamage() { return damage; }

	public double getAttackSpeed() { return attackSpeed; }
	
//	public int getUpdatesPerAttack() { return updatesPerAttack; }

	public double getAttackRange() { return attackRange; }
	
	public int getSpawnPrice() { return spawnPrice; }
	
	public int getPricePerHead() { return pricePerHead; }
	
	public double getHitBoxWidth() { return hitBoxWidth; }
	
	public double getHitBoxHeight() { return hitBoxHeight; }

	/* Setters */
	public void setRace(Race race) { this.race = race; }

	public void setMaxHp(int maxHp) { this.maxHp = maxHp; }
	
	public void setArmor(int armor) { this.armor = armor; }

	public void setDefaultSpeed(float defaultSpeed) {
		this.defaultSpeed = defaultSpeed / EngineV1.UPDATE_PER_SEC;
	}

	public void setDir(Vector2F dir) { this.dir = dir; }
//	public void setVelocityX(float velocityX) { this.velocityX = velocityX; }
//
//	public void setVelocityY(float velocityY) { this.velocityY = velocityY; }

	public void setDamage(int damage) { this.damage = damage; }

	public void setAttackSpeed(float attackSpeed) { 
		this.attackSpeed = attackSpeed / EngineV1.MS_PER_UPDATE;
//		this.updatesPerAttack = (int) (EngineV1.UPDATE_PER_SEC / attackSpeed);
	}

	public void setAttackRange(float attackRange) { this.attackRange = attackRange; }
	
	public void setSpawnPrice(int spawnPrice) { this.spawnPrice = spawnPrice; }
	
	public void setPricePerHead(int pricePerHead) { this.pricePerHead = pricePerHead; }
	
	public void setHitBoxWidth(float hitBoxWidth) { this.hitBoxWidth = hitBoxWidth; }
	
	public void setHitBoxHeight(float hitBoxHeight) { this.hitBoxHeight = hitBoxHeight; }
}
