package com.electdead.newgame.gameobject.unit;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.gameobject.GameObjectOld;
import com.electdead.newgame.gameobject.GameObjectType;
import com.electdead.newgame.gameobject.Side;
import com.electdead.newgame.gameobject.unit.actions.Action;
import com.electdead.newgame.gameobject.unit.actions.DieAction;
import com.electdead.newgame.gameobject.unit.ai.AIContainerOld;
import com.electdead.newgame.gamestate.DevGameState;
import com.electdead.newgame.graphics.UnitGraphicsModel;
import com.electdead.newgame.main.MainApp;
import com.electdead.newgame.physics.UnitPhysicsModel;
import com.electdead.newgame.physics.Vector2F;

import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Random;

public class UnitOld extends GameObjectOld {
    /* Common */
    public UnitPhysicsModel physModel;
    public UnitGraphicsModel graphModel;

    public AIContainerOld aiContainer;
    public Action action;

    /* Main */
    public int currHp;
    public int damage;
    public int armor;
    public float currentSpeed;
    public Vector2F moveDir;

    public Ellipse2D.Double hitBox;
    public UnitOld target;
    public Ellipse2D.Double attackBox;
    public Ellipse2D.Double searchCircle;

    public UnitOld(String name, Side side, GameObjectType type, float x, float y) {
        super(name, side, type, x, y);
        init();
    }

    private void init() {
        HashMap<String, Object> props = Assets.getProperties(name);
        physModel = (UnitPhysicsModel) props.get("physicsModel");
        graphModel = (UnitGraphicsModel) props.get("graphicsModel");

        hitBox = new Ellipse2D.Double();
        hitBox.setFrameFromCenter(
                pos.x, pos.y, pos.x + physModel.getHitBoxWidth(), pos.y + physModel.getHitBoxHeight());

        searchCircle = new Ellipse2D.Double();
        searchCircle.setFrameFromCenter(
                pos.x, pos.y, pos.x + physModel.getSearchRange(), pos.y + physModel.getSearchRange());

        attackBox = new Ellipse2D.Double();
        attackBox.setFrameFromCenter(
                pos.x, pos.y, pos.x + physModel.getAttackRange(), pos.y - physModel.getAttackRange());

        currHp			= physModel.getMaxHp();
        damage			= physModel.getDamage();
        armor 			= physModel.getArmor();
        currentSpeed	= physModel.getDefaultSpeed();
        moveDir         = physModel.getMoveDir();
    }

    public void setAIContainer(AIContainerOld aic) {
        this.aiContainer = aic;
    }

    public boolean isAlive() {
        return currHp > 0;
    }

    public void takeDamage(int damage) {
        int total = damage - armor;
        if (total <= 0) total = 1;
        currHp -= total;
        if (currHp <= 0) {
            zLevel = 0;
            target = null;
            aiContainer.locked = true;
            action = new DieAction(null, action);
            action.checkAnimationDir();
            drawBlood();
        }
    }

    @Override
    public boolean checkDelete() {
        if (physModel.getRace() == Race.Human) {
            if (pos.x >= 1280) {
                DevGameState.SWARM = false;
            }
        } else {
            if (pos.x <= 0) {
                DevGameState.SWARM = false;
            }
        }

        if ((pos.x + hitBox.width / 2 < -300) ||
                (pos.x - hitBox.width / 2) > MainApp.WIDTH + 300) {
            delete = true;
        }
        return delete;
    }

    public BufferedImage randomBlood() {
        int index = (int) (Math.random() * 7);
        return DevGameState.bloodSprites.get(index);
    }

    @Override
    public void update() {
        aiContainer.update();
        action.execute();
        super.update();
        checkDelete();
    }

//    @Override
//    public void updatePhysics() {
//
//    }

    private void drawBlood() {
        Random r = new Random();
        AffineTransform at = new AffineTransform();
        at.translate(pos.x, pos.y);
        at.rotate(Math.PI / r.nextInt(4));
        at.scale(r.nextDouble() + 0.2, r.nextDouble() + 0.2);

        for (int i = 0; i < 5; i++) {
            DevGameState.floorGraphics.drawImage(randomBlood(), at, null);
        }
    }

    @Override
    public void updateAi() {
        aiContainer.update();
    }

    @Override
    public void updateAction() {
        action.execute();
    }
}
