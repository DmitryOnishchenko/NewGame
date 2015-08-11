package com.electdead.newgame.gameobjects.components;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.gameobjects.UnitState;
import com.electdead.newgame.graphics.Animation;

public class UnitGraphicsComponent implements GraphicsComponent {
	private Unit unit;
	private UnitGraphicsModel model;
	private BufferedImage[] animationOld;
	private int currentSprite = 0;
	private int animationTimer = 0;
	
	private Animation animation;
	
	public UnitGraphicsComponent(Unit obj) {
		this.unit = obj;
		this.model = (UnitGraphicsModel) Assets.getProperties(obj.name).get("graphicsModel");
		this.animationOld = model.getMoveSprites();
	}

	@Override
    public void update() {
	    updateOld();
//	    updateNew();
    }
	
	public void updateOld() {
		if (++animationTimer >= model.getAnimationSpeed()) {
	    	currentSprite++;
	    	if (currentSprite == animationOld.length) {
	    		currentSprite = 0;
	    		
	    		if (unit.state == UnitState.FIGHT) {
	    			animationOld = model.getFightSprites();
	    		} else {
	    			animationOld = model.getMoveSprites();
	    		}
	    	}
	    	animationTimer = 0;
	    }
	}
	
	public void updateNew() {
		if (unit.state != unit.nextState) {
			animation.get(currentSprite);
		}
	}
	
	public UnitGraphicsModel getModel() {
		return model;
	}
	
	private int spriteX() {
		return (int) (unit.x - model.getWidthSprite() / 2);
	}
	
	private int spriteY() {
		return (int) (unit.y - model.getHeightSprite() + model.getBaseLine());
	}

	@Override
    public void render(Graphics2D g2, double deltaTime) {
//		g2.setPaint(Color.WHITE);
//		g2.draw(unit.hitBox);
//		g2.setPaint(Color.RED);
//		g2.draw(unit.attackBox);
		g2.drawImage(animationOld[currentSprite], spriteX(), spriteY(), null);
    }

}
