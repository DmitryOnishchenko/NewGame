package com.electdead.newgame.gameobjectV2.ai;

import com.electdead.newgame.gameobjectV2.BasicGameObject;
import com.electdead.newgame.gameobjectV2.action.Action;

public abstract class AiComponent {
    public int priority;
    public AiContainer container;
    public BasicGameObject object;
    public Action action;

    public AiComponent(AiContainer container, int priority) {
        this.container = container;
        this.object = container.object;
        this.priority = priority;
    }

    public abstract boolean think();

    public void setLock(boolean lock) {
        container.setLock(lock);
    }
}
