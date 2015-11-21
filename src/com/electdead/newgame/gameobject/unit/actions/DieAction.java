package com.electdead.newgame.gameobject.unit.actions;

import com.electdead.newgame.engine.EngineV2;
import com.electdead.newgame.gameobject.unit.UnitOld;
import com.electdead.newgame.graphics.AnimationOld;

import java.awt.image.BufferedImage;

public class DieAction extends Action {
    private int delayTimer = 0;
    private int deleteTrigger = 20000 / EngineV2.MS_PER_UPDATE;
    private boolean finished = false;
    private Action lastAction;
    private BufferedImage[] lastSprite;

    public DieAction(UnitOld unit, Action lastAction) {
        super(null, unit, true);
        BufferedImage[] dieSpritesRight = unit.graphModel.getDieSpritesRight();
        BufferedImage[] dieSpritesLeft = unit.graphModel.getDieSpritesLeft();
        animation = new AnimationOld(this, dieSpritesRight, dieSpritesRight, dieSpritesLeft);
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
        animation = new AnimationOld(this, lastSprite, lastSprite, lastSprite);
    }
}
