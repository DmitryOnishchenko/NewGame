package com.electdead.newgame.gameobjects.units;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.engine.Cell;
import com.electdead.newgame.gameobjects.GameObject;
import com.electdead.newgame.gameobjects.TypeObject;
import com.electdead.newgame.gameobjects.units.actions.Action;
import com.electdead.newgame.gameobjects.units.actions.DieAction;
import com.electdead.newgame.gameobjects.units.ai.AIContainer;
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

public class Unit extends GameObject {
    /* Grid cell */
    private Cell cell;
    public boolean relocate;

    /* Common */
    public UnitPhysicsModel physModel;
    public UnitGraphicsModel graphModel;

    public AIContainer aiContainer;
    public Action action;

    /* Main */
    public int currHp;
    public int damage;
    public int armor;
    public float currentSpeed;
    public Vector2F moveDir;

    public Ellipse2D.Double hitBox;
    public Unit target;
    public Ellipse2D.Double attackBox;
    public Ellipse2D.Double searchCircle;

    public Unit(String name, TypeObject type, float x, float y) {
        super(name, type, x, y);
        init();
    }

    private void init() {
        HashMap<String, Object> props = Assets.getProperties(name);
        physModel = (UnitPhysicsModel) props.get("physicsModel");
        graphModel = (UnitGraphicsModel) props.get("graphicsModel");

        hitBox = new Ellipse2D.Double();
        hitBox.setFrameFromCenter(pos.x, pos.y,
                pos.x + physModel.getHitBoxWidth(), pos.y + physModel.getHitBoxHeight());

        searchCircle = new Ellipse2D.Double();
        searchCircle.setFrameFromCenter(pos.x, pos.y, pos.x + physModel.getSearchRange(), pos.y + physModel.getSearchRange());

        currHp			= physModel.getMaxHp();
        damage			= physModel.getDamage();
        armor 			= physModel.getArmor();
        currentSpeed	= physModel.getDefaultSpeed();
        moveDir = physModel.getMoveDir();

        attackBox = new Ellipse2D.Double();
        if (moveDir.x > 0) {
            attackBox.setFrameFromCenter(pos.x, pos.y, pos.x + physModel.getAttackRange(), pos.y - physModel.getAttackRange());
        } else {
            attackBox.setFrameFromCenter(pos.x, pos.y, pos.x + physModel.getAttackRange(), pos.y - physModel.getAttackRange());
        }
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void setAIContainer(AIContainer aic) {
        this.aiContainer = aic;
    }

    public boolean isAlive() {
        return currHp > 0;
    }

    public boolean isEnemy(Unit unit) {
        return physModel.getRace() != unit.physModel.getRace();
    }

    public void takeDamage(int damage) {
        int total = damage - armor;
        if (total <= 0) total = 1;
        currHp -= total;
        if (currHp <= 0) {
            zLevel = 0;
            aiContainer.locked = true;
            action = new DieAction(this, action);
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
        aiContainer.update(this);
        action.execute();
        super.update();
        checkDelete();
    }

    private void drawBlood() {
        Random r = new Random();
        AffineTransform at = new AffineTransform();
        at.translate(pos.x, pos.y);
        at.rotate(Math.PI / r.nextInt(4));
        at.scale(r.nextDouble() + 0.2, r.nextDouble() + 0.2);
//		at.translate(-pos.x, -pos.y);
//		DevGameState.floorG2.drawImage(randomBlood(), x(), (int) (pos.y - hitBox.height / 2), null);
        for (int i = 0; i < 5; i++)
            DevGameState.floorG2.drawImage(randomBlood(), at, null);
    }
}
