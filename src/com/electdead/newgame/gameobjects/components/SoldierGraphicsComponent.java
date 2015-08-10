package com.electdead.newgame.gameobjects.components;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.gameobjects.GameObject;

public class SoldierGraphicsComponent implements GraphicsComponent {
	private GameObject object;
	private GraphicsModel model;
	private BufferedImage[] animation;
	private int currentSprite = 0;
	private int animationTimer = 0;
	
	public SoldierGraphicsComponent(GameObject obj) {
		this.object = obj;
		this.model = (GraphicsModel) Assets.getProperties(obj.name).get("graphicsModel");
		this.animation = model.getMoveSprites();
	}

	@Override
    public void update(GameObject obj) {
	    if (++animationTimer >= model.getAnimationSpeed()) {
	    	currentSprite++;
	    	if (currentSprite == animation.length) currentSprite = 0;
	    	animationTimer = 0;
	    }
    }
	
	public GraphicsModel getModel() {
		return model;
	}
	
	private int spriteX() {
		return (int) (object.x - model.getWidthSprite() / 2);
	}
	
	private int spriteY() {
		return (int) (object.y - model.getHeightSprite() + model.getBaseLine());
	}

	@Override
    public void render(Graphics2D g2, double deltaTime) {
		g2.drawImage(animation[currentSprite], spriteX(), spriteY(), null);
    }

}
