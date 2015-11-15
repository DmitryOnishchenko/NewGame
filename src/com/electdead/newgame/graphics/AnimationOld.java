package com.electdead.newgame.graphics;

import com.electdead.newgame.gameobject.unit.actions.Action;

import java.awt.image.BufferedImage;

public class AnimationOld {
    public Action action;
    private int currentSprite = 0;
    private int animationTimer;
    public BufferedImage[] sprites;
    private BufferedImage[] spritesRight;
    private BufferedImage[] spritesLeft;
    public int previousDirX;

    public AnimationOld(Action action, BufferedImage[] sprites,
                        BufferedImage[] spritesRight, BufferedImage[] spritesLeft) {
        this.action = action;
        this.sprites = sprites;
        this.spritesRight = spritesRight;
        this.spritesLeft = spritesLeft;
        this.previousDirX = 1;
        this.currentSprite = 0;
    }

    public void next() {
        if (++animationTimer >= action.unit.graphModel.getAnimationSpeed()) {
            currentSprite++;
            if (currentSprite == sprites.length) {
                currentSprite = 0;
                if (action.needFullAnimation)
                    action.animationFinished();
            }
            animationTimer = 0;
        }
    }

    public BufferedImage get() {
        return sprites[currentSprite];
    }

    public void swapDir(float unitDirX) {
        previousDirX = unitDirX > 0 ? 1 : -1;
        if (sprites == spritesRight) {
            sprites = spritesLeft;
        } else {
            sprites = spritesRight;
        }
    }

    public boolean watchWest() {
        return previousDirX < 0;
    }

    public boolean watchEast() {
        return previousDirX > 0;
    }
}
