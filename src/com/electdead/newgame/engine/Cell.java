package com.electdead.newgame.engine;

import com.electdead.newgame.gameobject.GameObjectType;
import com.electdead.newgame.gameobject.Side;
import com.electdead.newgame.gameobjectV2.BasicGameObject;
import com.electdead.newgame.gamestate.battle.BattleStateSettings;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Cell {
    private final int row;
    private final int col;
    private Grid grid;
    private Rectangle2D.Float bounds = new Rectangle2D.Float();

    private List<BasicGameObject> leftUnits = new FastRemoveArrayList<>(100);
    private List<BasicGameObject> rightUnits = new FastRemoveArrayList<>(100);
    private List<BasicGameObject> projectiles = new FastRemoveArrayList<>(100);

    private List<BasicGameObject> allObjects = new FastRemoveArrayList<>(1000);

    public Cell(Grid grid, int row, int col, Rectangle2D.Float bounds) {
        this.grid = grid;
        this.row = row;
        this.col = col;
        this.bounds = bounds;
    }

    public Rectangle2D getBounds() {
        return bounds;
    }

    public void update() {
        updateObjects(allObjects);
//        updateObjects(leftUnits);
//        updateObjects(rightUnits);
//        updateObjects(projectiles);
    }

    private void updateObjects(Collection<BasicGameObject> collection) {
        Iterator<BasicGameObject> iterator = collection.iterator();
        while (iterator.hasNext()) {
            BasicGameObject gameObject = iterator.next();
            if (gameObject.delete) {
                iterator.remove();
                grid.size--;
//                BattleStateSettings.NEED_DELETE = false;
            } else if (gameObject.relocate) {
                gameObject.relocate = false;
                iterator.remove();
                grid.rebase(gameObject);
//                BattleStateSettings.NEED_RELOCATE = false;
            } else {
                gameObject.updateAi();
                gameObject.updateAction();
                gameObject.updatePhysics();
                gameObject.updateGraphics();
            }
        }

//        for (BasicGameObject gameObject : collection) {
//            gameObject.updateAi();
//            gameObject.updateAction();
//            gameObject.updatePhysics();
//            gameObject.updateGraphics();
//        }
    }

    public void render(Graphics2D g2, double deltaTime) {
        if (BattleStateSettings.DEBUG_GRID) {
            g2.setPaint(Color.BLUE);
            g2.draw(bounds);
            g2.setPaint(Color.RED);
            g2.drawString(row + "|" + col, (int) (bounds.getX() + 2), (int) (bounds.getY() + 12));
        }

//        renderObjects(allObjects, g2, deltaTime);
    }

    private void renderObjects(Collection<BasicGameObject> collection, Graphics2D g2, double deltaTime) {
        for (BasicGameObject gameObject : collection) {
            gameObject.render(g2, deltaTime);
        }
    }

    public void clear() {
        leftUnits.clear();
        rightUnits.clear();
        projectiles.clear();
    }

    public List<BasicGameObject> getAllObjects() {
        List<BasicGameObject> list = getAllUnits();
        list.addAll(projectiles);

        return list;
    }

    public List<BasicGameObject> getAllUnits() {
//        allObjects.clear();
//        allObjects.addAll(leftUnits);
//        allObjects.addAll(rightUnits);

        return allObjects;
    }

    public List<BasicGameObject> getLeftUnits() {
        return leftUnits;
    }

    public List<BasicGameObject> getRightUnits() {
        return rightUnits;
    }

    public List<BasicGameObject> getProjectiles() {
        return projectiles;
    }

    public void add(BasicGameObject gameObject) {
        if (gameObject.type == GameObjectType.UNIT) {
//            addUnit(gameObject);
        } else if (gameObject.type == GameObjectType.PROJECTILE) {
//            addProjectile(gameObject);
        }
        allObjects.add(gameObject);
    }

    private void addUnit(BasicGameObject gameObject) {
        if (gameObject.side == Side.LEFT_ARMY) {
            leftUnits.add(gameObject);
        } else {
            rightUnits.add(gameObject);
        }
    }

    private void addProjectile(BasicGameObject gameObject) {
        projectiles.add(gameObject);
    }

    public void checkDelete() {
//        deleteObjects(leftUnits);
//        deleteObjects(rightUnits);
//        deleteObjects(projectiles);
    }

    public void move(BasicGameObject gameObject) {
        int newRow = (gameObject.y() - Grid.INDENT_TOP) / Grid.CELL_SIZE;
        int newCol = (gameObject.x() - Grid.INDENT_LEFT) / Grid.CELL_SIZE;

        if (gameObject.x() < Grid.INDENT_LEFT) {
            //TODO remake
            gameObject.delete = true;
            grid.size--;
//            BattleStateSettings.NEED_DELETE = true;
            return;
        }

        if (row != newRow || col != newCol) {
            gameObject.relocate = true;
//            BattleStateSettings.NEED_RELOCATE = true;
        }
    }

//    private void deleteObjects(List<BasicGameObject> collection) {
//        Iterator<BasicGameObject> iterator = collection.iterator();
//        while (iterator.hasNext()) {
//            BasicGameObject gameObject = iterator.next();
//            if (gameObject.delete) {
//                iterator.remove();
//                BattleStateSettings.NEED_DELETE = false;
//            } else if (gameObject.relocate) {
//                gameObject.relocate = false;
//                iterator.remove();
//                grid.add(gameObject);
//                BattleStateSettings.NEED_RELOCATE = false;
//            }
//        }
//    }

    public int sizeUnits() {
        return leftUnits.size() + rightUnits.size();
    }
}

