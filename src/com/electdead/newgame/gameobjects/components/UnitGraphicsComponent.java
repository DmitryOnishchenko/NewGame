package com.electdead.newgame.gameobjects.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.graphics.Animation;

public class UnitGraphicsComponent implements GraphicsComponent {
	private Unit unit;
	private UnitGraphicsModel model;
	private BufferedImage[] animationOld;
	private int currentSprite = 0;
	private int animationTimer = 0;
	
	public UnitGraphicsComponent(Unit obj) {
		this.unit = obj;
		this.model = (UnitGraphicsModel) Assets.getProperties(obj.name).get("graphicsModel");
		this.animationOld = model.getMoveSprites();
	}

	@Override
    public void update() {
//	    updateOld();
	    updateNew();
    }
	
	public void updateOld() {
		if (unit.target != null) {
			animationOld = model.getFightSprites();
			if (currentSprite >= animationOld.length)
				currentSprite = 0;
		} else {
			animationOld = model.getMoveSprites();
			if (currentSprite >= animationOld.length)
				currentSprite = 0;
		}
		
		if (++animationTimer >= model.getAnimationSpeed()) {
	    	currentSprite++;
	    	if (currentSprite == animationOld.length) {
	    		currentSprite = 0;
	    		
//	    		if (unit.target != null) {
//	    			animationOld = model.getFightSprites();
//	    		} else {
//	    			animationOld = model.getMoveSprites();
//	    		}
	    	}
	    	animationTimer = 0;
	    }
	}
	
	public void updateNew() {
		unit.action.animation.next();
	}
	
	public UnitGraphicsModel getModel() {
		return model;
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
//		g2.drawImage(animationOld[currentSprite], spriteX(), spriteY(), null);
		g2.drawImage(unit.action.animation.get(), spriteX(), spriteY(), null);
    }

}
