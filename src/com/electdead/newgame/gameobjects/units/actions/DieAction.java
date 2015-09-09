package com.electdead.newgame.gameobjects.units.actions;

import java.awt.image.BufferedImage;

import com.electdead.newgame.gameobjects.units.Unit;
import com.electdead.newgame.graphics.Animation;

public class DieAction extends Action {
	private int delay = 0;
	private boolean finished = false;
	private BufferedImage[] lastSprite;
	
	public DieAction(Unit unit) {
		super(null, unit, true);
		BufferedImage[] dieSprites = unit.graphModel.getDieSprites();
		animation = new Animation(this, dieSprites);
		lastSprite = new BufferedImage[] {dieSprites[dieSprites.length - 1]};
	}

	@Override
	public void execute() {
		if (finished && delay++ > 500) {
			unit.delete = true;
		}
	}

	@Override
	public void animationFinished() {
		finished = true;
		animation = new Animation(this, lastSprite);
	}
}
