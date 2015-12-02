package com.electdead.newgame.gameobjectV2;

import com.electdead.newgame.physics.Vector2F;

public class GameObjectState {
    public Vector2F pos;
    public volatile Vector2F moveDir;

    public volatile int currHp;
    public int damage;
    public int armor;
    public float currentSpeed;

    public void copyTo(GameObjectState target) {
        target.pos          = pos.copy();
        target.moveDir      = moveDir.copy();
        target.currHp       = currHp;
        target.damage       = damage;
        target.armor        = armor;
        target.currentSpeed = currentSpeed;
    }

    public boolean isAlive() {
        return currHp > 0;
    }
}
