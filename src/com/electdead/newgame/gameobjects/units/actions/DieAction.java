package com.electdead.newgame.gameobjects.units.actions;

import com.electdead.newgame.gameobjects.units.Unit;
import com.electdead.newgame.graphics.Animation;

import java.awt.image.BufferedImage;

public class DieAction extends Action {
    private int delay = 0;
    private boolean finished = false;
    private Action lastAction;
    private BufferedImage[] lastSprite;

    public DieAction(Unit unit, Action lastAction) {
        super(null, unit, true);
        BufferedImage[] dieSpritesRight = unit.graphModel.getDieSpritesRight();
        BufferedImage[] dieSpritesLeft = unit.graphModel.getDieSpritesLeft();
        animation = new Animation(this, dieSpritesRight, dieSpritesRight, dieSpritesLeft);
        this.lastAction = lastAction;
    }

    @Override
    public void execute() {
        if (finished && delay++ > 1000) {
            unit.delete = true;
        }
    }

    @Override
    public void checkAnimationDir() {
        if (lastAction.animation.watchWest()) {
            animation.swapDir(0);
        }
    }

    @Override
    public void animationFinished() {
        finished = true;
        lastSprite = new BufferedImage[] {animation.sprites[animation.sprites.length - 1]};
        animation = new Animation(this, lastSprite, lastSprite, lastSprite);
    }
}
