package com.electdead.newgame.gameobject.unit.actions;

import com.electdead.newgame.engine.EngineV1;
import com.electdead.newgame.gameobject.unit.Unit;
import com.electdead.newgame.graphics.Animation;

import java.awt.image.BufferedImage;

public class DieAction extends Action {
    private int delayTimer = 0;
    private int deleteTrigger = 20000 / EngineV1.MS_PER_UPDATE;
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
        if (finished && delayTimer++ > deleteTrigger) {
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
