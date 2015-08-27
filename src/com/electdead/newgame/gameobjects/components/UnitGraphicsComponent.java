package com.electdead.newgame.gameobjects.components;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.gameobjects.Unit;

public class UnitGraphicsComponent implements GraphicsComponent {
	private Unit unit;
	private UnitGraphicsModel model;
	
	public UnitGraphicsComponent(Unit obj) {
		this.unit = obj;
		this.model = (UnitGraphicsModel) Assets.getProperties(obj.name).get("graphicsModel");
	}

	@Override
    public void update() {
		unit.action.animation.next();
    }
	
	private int spriteX() {
		return (int) (unit.pos.x - model.getWidthSprite() / 2);
	}
	
	private int spriteY() {
		return (int) (unit.pos.y - model.getHeightSprite() + model.getBaseLine());
	}

	@Override
    public void render(Graphics2D g2, double deltaTime) {
//		g2.setPaint(Color.WHITE);
//		g2.draw(unit.hitBox);
//		g2.setPaint(Color.RED);
//		g2.draw(unit.attackBox);
		g2.drawImage(unit.action.animation.get(), spriteX(), spriteY(), null);
    }

}
