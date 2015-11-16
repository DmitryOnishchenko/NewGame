package com.electdead.newgame.gameobjectV2.action;

import com.electdead.newgame.gameobjectV2.Animation;
import com.electdead.newgame.gameobjectV2.BasicGameObject;
import com.electdead.newgame.gameobjectV2.ai.AiComponent;

public abstract class Action {
    public boolean needFullAnimation;
    public boolean wait;
    public AiComponent aiComponent;
    public BasicGameObject object;
    public Animation animation;

    //TODO
    public volatile int actionDelay;
    public int actionTrigger;

    public Action(AiComponent aiComponent, boolean needFullAnimation) {
        this.aiComponent = aiComponent;
        //TODO refactor
        if (aiComponent != null) {
            this.object = aiComponent.object;
        }
        this.needFullAnimation = needFullAnimation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public void checkAnimationDir() {
        float unitDirX = object.currentState.moveDir.x;
        if ((unitDirX > 0 && animation.watchWest()) ||
            (unitDirX < 0 && animation.watchEast())) {
            animation.swapDir(unitDirX);
        }
    }

    public abstract void execute();
    public abstract void animationFinished();
}
