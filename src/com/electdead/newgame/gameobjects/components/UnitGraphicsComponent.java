package com.electdead.newgame.gameobjects.components;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.gameobjects.Unit;
import com.electdead.newgame.gameobjects.UnitState;

public class UnitGraphicsComponent implements GraphicsComponent {
	private Unit unit;
	private UnitGraphicsModel model;
	private BufferedImage[] animation;
	private int currentSprite = 0;
	private int animationTimer = 0;
	
	public UnitGraphicsComponent(Unit obj) {
		this.unit = obj;
		this.model = (UnitGraphicsModel) Assets.getProperties(obj.name).get("graphicsModel");
		this.animation = model.getMoveSprites();
	}

	@Override
    public void update() {
	    if (++animationTimer >= model.getAnimationSpeed()) {
	    	currentSprite++;
	    	if (currentSprite == animation.length) {
	    		currentSprite = 0;
	    		
	    		if (unit.state == UnitState.MOVE) {
	    			animation = model.getMoveSprites();
	    			unit.currentSpeed = unit.physModel.getDefaultSpeed();
	    		} else if (unit.state == UnitState.FIGHT) {
	    			animation = model.getFightSprites();
	    		} else if (unit.state == UnitState.STAND) {
	    			animation = model.getDieSprites();
	    		}
	    	}
	    	animationTimer = 0;
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
		g2.drawImage(animation[currentSprite], spriteX(), spriteY(), null);
    }

}
