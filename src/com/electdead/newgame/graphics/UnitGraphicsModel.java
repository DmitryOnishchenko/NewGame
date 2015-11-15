package com.electdead.newgame.graphics;

import com.electdead.newgame.assets.ImageUtils;
import com.electdead.newgame.engine.EngineV1Old;

import java.awt.image.BufferedImage;

public class UnitGraphicsModel {
    private int widthSprite;
    private int heightSprite;
    private int baseLine;
    private int animationSpeed;
    private BufferedImage[] moveSpritesRight;
    private BufferedImage[] moveSpritesLeft;
    private BufferedImage[] fightSpritesRight;
    private BufferedImage[] fightSpritesLeft;
    private BufferedImage[] dieSpritesRight;
    private BufferedImage[] dieSpritesLeft;

    public UnitGraphicsModel() {}

    /* Getters */
    public int getWidthSprite() { return widthSprite; }

    public int getHeightSprite() { return heightSprite; }

    public int getBaseLine() { return baseLine; }

    public int getAnimationSpeed() { return animationSpeed; }

    public BufferedImage[] getMoveSpritesRight() { return moveSpritesRight; }

    public BufferedImage[] getMoveSpritesLeft() { return moveSpritesLeft; }

    public BufferedImage[] getFightSpritesRight() { return fightSpritesRight; }

    public BufferedImage[] getFightSpritesLeft() { return fightSpritesLeft; }

    public BufferedImage[] getDieSpritesRight() { return dieSpritesRight; }

    public BufferedImage[] getDieSpritesLeft() { return dieSpritesLeft; }

    /* Setters */
    public void setWidthSprite(int widthSprite) { this.widthSprite = widthSprite; }

    public void setHeightSprite(int heightSprite) { this.heightSprite = heightSprite; }

    public void setBaseLine(int baseLine) { this.baseLine = baseLine; }

    public void setAnimationSpeed(int animationSpeedMs) {
        this.animationSpeed = animationSpeedMs / EngineV1Old.MS_PER_UPDATE;
    }

    public void setMoveSpritesRight(BufferedImage[] moveSpritesRight) {
        this.moveSpritesRight = moveSpritesRight;
        this.moveSpritesLeft = createMirror(moveSpritesRight);
    }

    public void setFightSpritesRight(BufferedImage[] fightSpritesRight) {
        this.fightSpritesRight = fightSpritesRight;
        this.fightSpritesLeft = createMirror(fightSpritesRight);
    }

    public void setDieSpritesRight(BufferedImage[] dieSpritesRight) {
        this.dieSpritesRight = dieSpritesRight;
        this.dieSpritesLeft = createMirror(dieSpritesRight);
    }

    private BufferedImage[] createMirror(BufferedImage[] src) {
        BufferedImage[] dst = new BufferedImage[src.length];
        System.arraycopy(src, 0, dst, 0, src.length);
        ImageUtils.flipHorizontally(dst);
        return dst;
    }
}
