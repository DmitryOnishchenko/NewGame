package com.electdead.newgame.gameobjects.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.gameobjects.Race;
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
//		g2.draw(unit.searchCircle);
//		g2.setPaint(Color.RED);
//		g2.draw(unit.attackBox);
		
		/* !!!!!!!!!!! DELETE THIS !!!!!!!!!!!!*/
		BufferedImage image = unit.action.animation.get();
		
		if (unit.physModel.getRace() == Race.Human) {
			if (unit.target != null && unit.pos.x > unit.target.pos.x) {
				// Flip the image horizontally
				AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
				tx.translate(-image.getWidth(null), 0);
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				image = op.filter(image, null);
			}
		} else {
			if (unit.target != null && unit.pos.x < unit.target.pos.x) {
				// Flip the image horizontally
				AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
				tx.translate(-image.getWidth(null), 0);
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				image = op.filter(image, null);
			}
		}
		
//		if (unit.target != null) {
//			if (unit.physModel.getRace() == Race.Human) g2.setPaint(Color.YELLOW);
//			else g2.setPaint(Color.BLUE);
//			g2.drawLine(unit.x(), unit.y(), unit.target.x(), unit.target.y());
//		}
		
//		g2.drawImage(unit.action.animation.get(), spriteX(), spriteY(), null);
		g2.drawImage(image, spriteX(), spriteY(), null);
		
    }

}
