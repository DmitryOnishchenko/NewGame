package com.electdead.newgame.gameobjects.components;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.gameobjects.GameObject;

public class GraphicsComponentOld {
	private GameObject obj;
	private UnitGraphicsModel graphModel;
	private int currentSprite;
	private BufferedImage[] currentAnimation;
	private int animTimer;
	
	public GraphicsComponentOld(GameObject obj) {
		this.obj = obj;
		init();
	}
	
	public void init() {
		HashMap<String, Object> props = Assets.getProperties(obj.name);
		graphModel = (UnitGraphicsModel) props.get("graphModel");
		
//		currentSprite = graphModel.getDefaultSpriteIndex();
	}

	public void update() {
		if (++animTimer > graphModel.getAnimationSpeed()) {
			currentSprite++;
			animTimer = 0;
		}
		if (currentSprite == 12) {
//			currentSprite = graphModel.getDefaultSpriteIndex();
		}
	}
	
	private int spriteX() {
		return (int) (obj.x - graphModel.getWidthSprite() / 2);
	}
	
	private int spriteY() {
		return (int) (obj.y - graphModel.getHeightSprite() + graphModel.getBaseLine());
	}
	
	public void render(Graphics2D g2, double deltaTime) {
//	    g2.setPaint(Color.WHITE);
//		g2.draw(obj.getHitBox());
//		g2.setPaint(Color.RED);
//		g2.draw(obj.getAttackBox());
//	    g2.drawImage(graphModel.getSprite(currentSprite), spriteX(), spriteY(), null);
//	    g2.drawRect(spriteX(), spriteY(), wSprite, hSprite);
//	    g2.drawLine(obj.x(), obj.y(), obj.x(), obj.y());
    }
}
