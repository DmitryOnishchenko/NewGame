package com.electdead.newgame.gameobjects.units.actions;

import com.electdead.newgame.gameobjects.units.Unit;
import com.electdead.newgame.graphics.Animation;

import java.awt.image.BufferedImage;

public class DieAction extends Action {
    private int delay = 0;
    private boolean finished = false;
    private BufferedImage[] lastSprite;

    public DieAction(Unit unit) {
        super(null, unit, true);
        BufferedImage[] dieSpritesRight = unit.graphModel.getDieSpritesRight();
        BufferedImage[] dieSpritesLeft = unit.graphModel.getDieSpritesLeft();
        animation = new Animation(this, dieSpritesRight, dieSpritesRight, dieSpritesLeft);
//        lastSprite = new BufferedImage[] {dieSpritesRight[dieSpritesRight.length - 1]};
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
        lastSprite = new BufferedImage[] {animation.sprites[animation.sprites.length - 1]};
        animation = new Animation(this, lastSprite, lastSprite, lastSprite);
    }
}
