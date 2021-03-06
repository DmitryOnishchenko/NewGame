package com.electdead.newgame.gameobject;

import com.electdead.newgame.engine.CellOld;
import com.electdead.newgame.gameobject.unit.actions.Action;
import com.electdead.newgame.gameobject.unit.ai.AIContainerOld;
import com.electdead.newgame.graphics.GraphicsComponent;
import com.electdead.newgame.input.InputComponent;
import com.electdead.newgame.main.MainApp;
import com.electdead.newgame.physics.PhysicsComponent;
import com.electdead.newgame.physics.Vector2F;
import com.google.common.base.Joiner;

import java.awt.*;

public class GameObjectOld implements Comparable<GameObjectOld> {
    public static int ID = 0;

    /* GridOld cell */
    private CellOld cell;
    public boolean relocate;

    public final int id;
    public String name;
    public Vector2F pos;
    public int zLevel = 1;
    public Side side;
    public GameObjectType type;
    public boolean visible = true;
    public boolean delete = false;

    private InputComponent inputComponent;
    private PhysicsComponent physicsComponent;
    private GraphicsComponent graphicsComponent;

    public AIContainerOld aiContainer;
    public Action action;

    public GameObjectOld(String name, Side side, GameObjectType type, float x, float y) {
        this.id = ++ID;
        this.name = name;
        this.pos = new Vector2F(x, y);
        this.side = side;
        this.type = type;
    }

    public InputComponent getInput() {
        return inputComponent;
    }

    public void setPhysicsComponent(PhysicsComponent pc) {
        this.physicsComponent = pc;
    }

    public void setGraphicsComponent(GraphicsComponent gc) {
        this.graphicsComponent = gc;
    }

    public int x() {
        return (int) pos.x;
    }

    public int y() {
        return (int) pos.y;
    }

    public CellOld getCell() {
        return cell;
    }

    public void setCell(CellOld cell) {
        this.cell = cell;
    }

    public void update() {
        if (inputComponent != null)
            inputComponent.update();
        if (physicsComponent != null)
            physicsComponent.update();
        if (graphicsComponent != null)
            graphicsComponent.update();
    }

    public void setAiContainer(AIContainerOld aiContainer) {
        this.aiContainer = aiContainer;
    }

    //TODO Ai update thread
    public void updateAi() {
//        if (this instanceof UnitOld && aiContainer != null) aiContainer.update((UnitOld) this);
    }

    //TODO Action execute thread
    public void updateAction() {
        if (action != null) action.execute();
    }

    //TODO Physics update thread
//    public void updatePhysics();

    //TODO Graphics update thread
    public void updateGraphics() {
        if (graphicsComponent != null) graphicsComponent.update();
    }

    public void render(Graphics2D g2, double deltaTime) {
        if (graphicsComponent != null)
            graphicsComponent.render(g2, deltaTime);
    }

    public boolean checkDelete() {
        if ((pos.x < 0) ||
                (pos.x) > MainApp.WIDTH) {
            delete = true;
        }
        return delete;
    }

    public String toString() {
        return Joiner.on("").join("GameObjectOld[x: ", pos.x, ", y:", pos.y, "]");
    }

    public int compareTo(GameObjectOld obj) {
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

    public void updateInput() {
        if (inputComponent != null) {
            inputComponent.update();
        }
    }
}
