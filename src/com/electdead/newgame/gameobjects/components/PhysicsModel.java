package com.electdead.newgame.gameobjects.components;

import com.electdead.newgame.engine.EngineV1;
import com.electdead.newgame.gameobjects.Race;

public class PhysicsModel {
	private Race race;
	private int maxHp;
	private int armor;
	private double defaultSpeed;
	private int velocityX;
	private int velocityY;
	private int damage;
	private double attackSpeed;
	private int updatesPerAttack;
	private double attackRange;
	private int spawnPrice;
	private int pricePerHead;
	private double hitBoxWidth;
	private double hitBoxHeight;
	
	public PhysicsModel() {}
	
	/* Getters */
	public Race getRace() { return race; }

	public int getMaxHp() {	return maxHp; }
	
	public int getArmor() {	return armor; }

	public double getDefaultSpeed() { return defaultSpeed; }

	public int getVelocityX() {	return velocityX; }

	public int getVelocityY() {	return velocityY; }

	public int getDamage() { return damage; }

	public double getAttackSpeed() { return attackSpeed; }
	
	public int getUpdatesPerAttack() { return updatesPerAttack; }

	public double getAttackRange() { return attackRange; }
	
	public int getSpawnPrice() { return spawnPrice; }
	
	public int getPricePerHead() { return pricePerHead; }
	
	public double getHitBoxWidth() { return hitBoxWidth; }
	
	public double getHitBoxHeight() { return hitBoxHeight; }

	/* Setters */
	public void setRace(Race race) { this.race = race; }

	public void setMaxHp(int maxHp) { this.maxHp = maxHp; }
	
	public void setArmor(int armor) { this.armor = armor; }

	public void setDefaultSpeed(double defaultSpeed) {
		this.defaultSpeed = defaultSpeed / EngineV1.UPDATE_PER_SEC;
	}

	public void setVelocityX(int velocityX) { this.velocityX = velocityX; }

	public void setVelocityY(int velocityY) { this.velocityY = velocityY; }

	public void setDamage(int damage) { this.damage = damage; }

	public void setAttackSpeed(double attackSpeed) { 
		this.attackSpeed = attackSpeed;
		this.updatesPerAttack = (int) (EngineV1.UPDATE_PER_SEC / attackSpeed);
	}

	public void setAttackRange(double attackRange) { this.attackRange = attackRange; }
	
	public void setSpawnPrice(int spawnPrice) { this.spawnPrice = spawnPrice; }
	
	public void setPricePerHead(int pricePerHead) { this.pricePerHead = pricePerHead; }
	
	public void setHitBoxWidth(double hitBoxWidth) { this.hitBoxWidth = hitBoxWidth; }
	
	public void setHitBoxHeight(double hitBoxHeight) { this.hitBoxHeight = hitBoxHeight; }
}
