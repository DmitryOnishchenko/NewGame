package com.electdead.newgame.graphics;

import com.electdead.newgame.gameobjects.units.actions.Action;
import com.electdead.newgame.physics.Vector2F;

import java.awt.image.BufferedImage;

public class Animation {
    public Action action;
    private int currentSprite = 0;
    private int animationTimer;
    public BufferedImage[] sprites;
    private BufferedImage[] spritesRight;
    private BufferedImage[] spritesLeft;
    private int dirX;

    public Animation(Action action, BufferedImage[] sprites,
                     BufferedImage[] spritesRight, BufferedImage[] spritesLeft) {
        this.action = action;
        this.sprites = sprites;
        this.spritesRight = spritesRight;
        this.spritesLeft = spritesLeft;
        this.dirX = 1;
        this.currentSprite = 0;
        checkDir(action.unit.dir);
    }

    public void next() {
        if (++animationTimer >= action.unit.graphModel.getAnimationSpeed()) {
//            checkDir(action.unit.dir);

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

    public void checkDir(Vector2F unitDir) {
        int unitX = 0;
        if (unitDir.x > 0) {
            unitX = 1;
        } else {
            unitX = -1;
        }
        if (unitX != dirX) {
            swapDir(unitX);
            dirX = unitX;
        }
    }

    private void swapDir(int unitX) {
        if (unitX == 1) {
            sprites = spritesRight;
        } else {
            sprites = spritesLeft;
        }
    }
}
