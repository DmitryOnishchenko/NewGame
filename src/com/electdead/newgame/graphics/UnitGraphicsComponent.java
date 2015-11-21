package com.electdead.newgame.graphics;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.gameobject.Side;
import com.electdead.newgame.gameobjectV2.BasicGameObject;
import com.electdead.newgame.gamestate.battle.BattleStateSettings;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnitGraphicsComponent implements GraphicsComponent {
    private BasicGameObject gameObject;
    private UnitGraphicsModel model;

    public UnitGraphicsComponent(BasicGameObject obj) {
        this.gameObject = obj;
        this.model = (UnitGraphicsModel) Assets.getProperties(obj.name).get("graphicsModel");
    }

    @Override
    public void update() {
        if (gameObject.action != null) gameObject.action.animation.next();
    }

    private int spriteX() {
        return (int) (gameObject.currentState.pos.x - model.getWidthSprite() / 2);
    }

    private int spriteY() {
        return (int) (gameObject.currentState.pos.y - model.getHeightSprite() + model.getBaseLine());
    }

    @Override
    public void render(Graphics2D g2, double deltaTime) {
        // Render if visible
        if (gameObject.visible) {
            if (BattleStateSettings.DEBUG_MODE) {
                if (gameObject.currentState.isAlive() && BattleStateSettings.DEBUG_BOX) {
                    g2.setPaint(Color.WHITE);
                    g2.draw(gameObject.hitBox);
                    g2.draw(gameObject.searchCircle);
                    g2.setPaint(Color.RED);
                    g2.draw(gameObject.attackBox);
                }
                if (gameObject.target != null && BattleStateSettings.DEBUG_TARGET) {
                    g2.setPaint(gameObject.side == Side.LEFT_ARMY ? Color.YELLOW : Color.BLUE);
                    g2.drawLine(gameObject.x(), gameObject.y(), gameObject.target.x(), gameObject.target.y());
                }
            }

            BufferedImage image = gameObject.action.animation.get();
            g2.drawImage(image, spriteX(), spriteY(), null);
        }
    }

}
