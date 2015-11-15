package com.electdead.newgame.gameobject.unit.actions;

import com.electdead.newgame.gameobject.unit.UnitOld;
import com.electdead.newgame.gameobject.unit.ai.AIComponentOld;
import com.electdead.newgame.graphics.AnimationOld;

public abstract class Action {
    public boolean needFullAnimation;
    public boolean wait;
    public AIComponentOld aiComponent;
    public UnitOld unit;
    public AnimationOld animation;

    //TODO
    public int actionDelay;
    public int actionTrigger;

    public Action(AIComponentOld aiComponent, UnitOld gameObject, boolean needFullAnimation) {
        this.aiComponent = aiComponent;
        this.unit = gameObject;
        this.needFullAnimation = needFullAnimation;
    }

    public void setAnimation(AnimationOld animation) {
        this.animation = animation;
    }

    public void checkAnimationDir() {
        float unitDirX = unit.moveDir.x;
        if ((unitDirX > 0 && animation.watchWest()) ||
            (unitDirX < 0 && animation.watchEast())) {
            animation.swapDir(unitDirX);
        }
    }


    public abstract void execute();
    public abstract void animationFinished();
}
