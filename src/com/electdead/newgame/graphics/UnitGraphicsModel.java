package com.electdead.newgame.graphics;

import com.electdead.newgame.assets.ImageUtils;
import com.electdead.newgame.engine.EngineV1;

import java.awt.image.BufferedImage;

public class UnitGraphicsModel {
    private int widthSprite;
    private int heightSprite;
    private int baseLine;
    private BufferedImage[] moveSpritesRight;
    private BufferedImage[] moveSpritesLeft;
    private BufferedImage[] fightSprites;
    private BufferedImage[] dieSprites;
    private int animationSpeed;

    public UnitGraphicsModel() {}

    /* Getters */
    public int getWidthSprite() { return widthSprite; }

    public int getHeightSprite() { return heightSprite; }

    public int getBaseLine() { return baseLine; }

    public BufferedImage[] getMoveSpritesRight() { return moveSpritesRight; }

    public BufferedImage[] getMoveSpritesLeft() { return moveSpritesLeft; }

    public BufferedImage[] getFightSprites() { return fightSprites; }

    public BufferedImage[] getDieSprites() { return dieSprites; }

    public int getAnimationSpeed() { return animationSpeed; }

    /* Setters */
    public void setWidthSprite(int widthSprite) { this.widthSprite = widthSprite; }

    public void setHeightSprite(int heightSprite) { this.heightSprite = heightSprite; }

    public void setBaseLine(int baseLine) { this.baseLine = baseLine; }

    public void setMoveSpritesRight(BufferedImage[] moveSpritesRight) {
        this.moveSpritesRight = moveSpritesRight;
        this.moveSpritesLeft = new BufferedImage[moveSpritesRight.length];
        System.arraycopy(moveSpritesRight, 0, moveSpritesLeft, 0, moveSpritesRight.length);
        ImageUtils.flipHorizontally(moveSpritesLeft);
    }

    public void setFightSprites(BufferedImage[] fightSprites) { this.fightSprites = fightSprites; }

    public void setDieSprites(BufferedImage[] dieSprites) { this.dieSprites = dieSprites; }

    public void setAnimationSpeed(int animationSpeedMs) { this.animationSpeed = animationSpeedMs / EngineV1.MS_PER_UPDATE; }
}
