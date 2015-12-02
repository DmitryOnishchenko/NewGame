package com.electdead.newgame.gameobjectV2;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.engine.Cell;
import com.electdead.newgame.gameobject.GameObjectType;
import com.electdead.newgame.gameobject.Side;
import com.electdead.newgame.gameobjectV2.action.Action;
import com.electdead.newgame.gameobjectV2.action.DieAction;
import com.electdead.newgame.gameobjectV2.ai.AiContainer;
import com.electdead.newgame.graphics.GraphicsComponent;
import com.electdead.newgame.graphics.UnitGraphicsComponent;
import com.electdead.newgame.graphics.UnitGraphicsModel;
import com.electdead.newgame.input.InputComponent;
import com.electdead.newgame.physics.PhysicsComponent;
import com.electdead.newgame.physics.UnitPhysicsModel;
import com.electdead.newgame.physics.Vector2F;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;

public class BasicGameObject extends GameObject<BasicGameObject> {
    public static int ID = 1;

    /* Common fields */
    public final int id = ID++;
    public volatile Cell cell;
    public final String name;
    public Side side;
    public GameObjectType type;
    public int zLevel = 1;

    /* Physics and Graphics models */
    public UnitPhysicsModel pModel;
    public UnitGraphicsModel gModel;

    /* Target */
    public BasicGameObject target;

    /* Box model */
    public Ellipse2D.Double hitBox;
    public Ellipse2D.Double attackBox;
    public Ellipse2D.Double searchCircle;

    /* Flags */
    public boolean visible = true;
//    public boolean delete = false;
    public boolean relocate = false;

    /* Double buffering */
    //TODO try double buffering
    public GameObjectState currentState = new GameObjectState();
    public GameObjectState nextState = new GameObjectState();

    /* Updaters */
    public InputComponent inputComponent;
    public AiContainer aiContainer;
    public Action action;
    public PhysicsComponent physicsComponent;
    public GraphicsComponent graphicsComponent;

    public BasicGameObject(String name, float x, float y) {
        this.name               = name;
        this.currentState.pos   = new Vector2F(x, y);
        this.nextState.pos      = this.currentState.pos.copy();

        init();
    }

    @Override
    public void init() {
        HashMap<String, Object> props = Assets.getProperties(name);
        pModel = (UnitPhysicsModel) props.get("physicsModel");
        gModel = (UnitGraphicsModel) props.get("graphicsModel");

        currentState.currHp			= pModel.getMaxHp();
        currentState.damage			= pModel.getDamage();
        currentState.armor 			= pModel.getArmor();
        currentState.currentSpeed	= pModel.getDefaultSpeed();
        currentState.moveDir        = pModel.getMoveDir();

        side = (Side) props.get("side");
        type = GameObjectType.UNIT;

        float posX = currentState.pos.x;
        float posY = currentState.pos.y;

        hitBox = new Ellipse2D.Double();
        hitBox.setFrameFromCenter(posX, posY, posX + pModel.getHitBoxWidth(), posY + pModel.getHitBoxHeight());

        searchCircle = new Ellipse2D.Double();
        searchCircle.setFrameFromCenter(posX, posY, posX + pModel.getSearchRange(), posY + pModel.getSearchRange());

        attackBox = new Ellipse2D.Double();
        attackBox.setFrameFromCenter(posX, posY, posX + pModel.getAttackRange(), posY - pModel.getAttackRange());

        // Components
        aiContainer         = new AiContainer(this);
        graphicsComponent   = new UnitGraphicsComponent(this);
    }

    @Override
    public void updateInput(KeyEvent event) {
        if (inputComponent != null) inputComponent.update();
    }

    @Override
    public void updateAi() {
        if (aiContainer != null) aiContainer.update();
    }

    @Override
    public void updateAction() {
        if (action != null) action.execute();
    }

    @Override
    public void updatePhysics() {
        if (physicsComponent != null) physicsComponent.update();
    }

    @Override
    public void updateGraphics() {
        if (graphicsComponent != null) graphicsComponent.update();
    }

    @Override
    public void render(Graphics2D graphics, double deltaTime) {
        if (graphicsComponent != null && action != null) graphicsComponent.render(graphics, deltaTime);
    }

    @Override
    public void switchState() {
        GameObjectState temp = currentState;
        currentState = nextState;
        nextState = temp;
    }

    @Override
    public int compareTo(BasicGameObject obj) {
        int result = zLevel - obj.zLevel;
        if (result == 0) {
            result = y() - obj.y();
        }
        if (result == 0) {
            result = x() - obj.x();
        }
        if (result == 0) {
            result = id - obj.id;
        }

        return result;
    }

    public int x() {
        return (int) currentState.pos.x;
    }

    public int y() {
        return (int) currentState.pos.y;
    }

    public void takeDamage(int damage) {
        int total = damage - currentState.armor;
        if (total <= 0) total = 1;
        currentState.currHp -= total;
        if (currentState.currHp <= 0) {
            zLevel = 0;
            target = null;
            aiContainer.locked = true;
            action = new DieAction(this, action);
            action.checkAnimationDir();
//            drawBlood();
        }
    }

    @Override
    public String toString() {
        return name + ": " + currentState.currHp + "/" + pModel.getMaxHp() + " | pos[" + x() + " - " + y() + "]";
    }
}
