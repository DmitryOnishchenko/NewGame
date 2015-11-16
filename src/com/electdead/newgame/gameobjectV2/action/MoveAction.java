package com.electdead.newgame.gameobjectV2.action;

import com.electdead.newgame.gameobjectV2.ai.AiComponent;

public class MoveAction extends Action {

    public MoveAction(AiComponent aiComponent, boolean needFullAnimation) {
        super(aiComponent, needFullAnimation);
    }

    @Override
    public void execute() {
        if (!object.currentState.isAlive()) {
            return;
        }

        checkAnimationDir();

        float shiftX                = object.currentState.currentSpeed * object.currentState.moveDir.x;
        object.currentState.pos.x   += shiftX;
        object.hitBox.x             += shiftX;
        object.attackBox.x          += shiftX;
        object.searchCircle.x       += shiftX;

        float shiftY                = object.currentState.currentSpeed * object.currentState.moveDir.y;
        object.currentState.pos.y   += shiftY;
        object.hitBox.y             += shiftY;
        object.attackBox.y          += shiftY;
        object.searchCircle.y       += shiftY;

        object.cell.move(object);
    }

    @Override
    public void animationFinished() {
    }
}
