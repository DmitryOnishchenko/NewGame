package com.electdead.newgame.graphics;

import com.electdead.newgame.assets.ImageUtils;
import com.electdead.newgame.engine.EngineV2;

import java.awt.image.VolatileImage;

public class UnitGraphicsModel {
    private int widthSprite;
    private int heightSprite;
    private int baseLine;
    private int animationSpeed;
    private VolatileImage[] moveSpritesRight;
    private VolatileImage[] moveSpritesLeft;
    private VolatileImage[] fightSpritesRight;
    private VolatileImage[] fightSpritesLeft;
    private VolatileImage[] dieSpritesRight;
    private VolatileImage[] dieSpritesLeft;

    public UnitGraphicsModel() {}

    /* Getters */
    public int getWidthSprite() { return widthSprite; }

    public int getHeightSprite() { return heightSprite; }

    public int getBaseLine() { return baseLine; }

    public int getAnimationSpeed() { return animationSpeed; }

    public VolatileImage[] getMoveSpritesRight() { return moveSpritesRight; }

    public VolatileImage[] getMoveSpritesLeft() { return moveSpritesLeft; }

    public VolatileImage[] getFightSpritesRight() { return fightSpritesRight; }

    public VolatileImage[] getFightSpritesLeft() { return fightSpritesLeft; }

    public VolatileImage[] getDieSpritesRight() { return dieSpritesRight; }

    public VolatileImage[] getDieSpritesLeft() { return dieSpritesLeft; }

    /* Setters */
    public void setWidthSprite(int widthSprite) { this.widthSprite = widthSprite; }

    public void setHeightSprite(int heightSprite) { this.heightSprite = heightSprite; }

    public void setBaseLine(int baseLine) { this.baseLine = baseLine; }

    public void setAnimationSpeed(int animationSpeedMs) {
        this.animationSpeed = animationSpeedMs / EngineV2.MS_PER_UPDATE;
    }

    public void setMoveSpritesRight(VolatileImage[] moveSpritesRight) {
        this.moveSpritesRight = moveSpritesRight;
        this.moveSpritesLeft = createMirror(moveSpritesRight);
    }

    public void setFightSpritesRight(VolatileImage[] fightSpritesRight) {
        this.fightSpritesRight = fightSpritesRight;
        this.fightSpritesLeft = createMirror(fightSpritesRight);
    }

    public void setDieSpritesRight(VolatileImage[] dieSpritesRight) {
        this.dieSpritesRight = dieSpritesRight;
        this.dieSpritesLeft = createMirror(dieSpritesRight);
    }

    private VolatileImage[] createMirror(VolatileImage[] src) {
        VolatileImage[] dst = new VolatileImage[src.length];
        System.arraycopy(src, 0, dst, 0, src.length);
        ImageUtils.flipHorizontally(dst);
        return dst;
    }
}
