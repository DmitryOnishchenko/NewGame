package com.electdead.newgame.graphics;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.gameobjects.units.Unit;
import com.electdead.newgame.gamestate.DevGameState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnitGraphicsComponent implements GraphicsComponent {
    private Unit unit;
    private UnitGraphicsModel model;

    public UnitGraphicsComponent(Unit obj) {
        this.unit = obj;
        this.model = (UnitGraphicsModel) Assets.getProperties(obj.name).get("graphicsModel");
    }

    @Override
    public void update() {
        unit.action.animation.next();
    }

    private int spriteX() {
        return (int) (unit.pos.x - model.getWidthSprite() / 2);
    }

    private int spriteY() {
        return (int) (unit.pos.y - model.getHeightSprite() + model.getBaseLine());
    }

    @Override
    public void render(Graphics2D g2, double deltaTime) {
        if (DevGameState.DEBUG) {
            g2.setPaint(Color.WHITE);
            g2.draw(unit.hitBox);
            g2.draw(unit.searchCircle);
            g2.setPaint(Color.RED);
            g2.draw(unit.attackBox);
        }

        BufferedImage image = unit.action.animation.get();
        g2.drawImage(image, spriteX(), spriteY(), null);

    }

}
