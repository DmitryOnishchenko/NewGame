package com.electdead.newgame.gameobjectV2.action;

import com.electdead.newgame.engine.EngineV2;
import com.electdead.newgame.gameobjectV2.Animation;
import com.electdead.newgame.gameobjectV2.BasicGameObject;

import java.awt.image.BufferedImage;

public class DieAction extends Action {
    private int delayTimer = 0;
    private int deleteTrigger = 20000 / EngineV2.MS_PER_UPDATE;
    private boolean finished = false;
    private Action lastAction;
    private BufferedImage[] lastSprite;

    public DieAction(BasicGameObject object, Action lastAction) {
        super(null, true);
        this.object = object;
        BufferedImage[] dieSpritesRight = object.gModel.getDieSpritesRight();
        BufferedImage[] dieSpritesLeft = object.gModel.getDieSpritesLeft();
        animation = new Animation(this, dieSpritesRight, dieSpritesRight, dieSpritesLeft);
        this.lastAction = lastAction;
    }

    @Override
    public void execute() {
        if (finished && delayTimer++ > deleteTrigger) {
            object.delete = true;
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
