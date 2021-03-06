package com.electdead.newgame.engine;

import com.electdead.newgame.gameobject.GameObjectOld;
import com.electdead.newgame.gameobject.GameObjectType;
import com.electdead.newgame.gameobject.Side;
import com.electdead.newgame.gameobjectV2.BasicGameObject;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

public class CellOld {
    private final int row;
    private final int col;
    private GridOld grid;
    private Rectangle2D.Float bounds = new Rectangle2D.Float();

    private List<BasicGameObject> leftUnits = new FastRemoveArrayList<>(100);
    private List<BasicGameObject> rightUnits = new FastRemoveArrayList<>(100);
    private List<BasicGameObject> projectiles = new FastRemoveArrayList<>(100);

    private List<BasicGameObject> list = new FastRemoveArrayList<>(1000);

    public CellOld(GridOld grid, int row, int col, Rectangle2D.Float bounds) {
        this.grid = grid;
        this.row = row;
        this.col = col;
        this.bounds = bounds;
    }

    public Rectangle2D getBounds() {
        return bounds;
    }

    public void update() {
        updateObjects(leftUnits);
        updateObjects(rightUnits);
        updateObjects(projectiles);
    }

    public void renderCell(Graphics2D g2, double deltaTime) {
        g2.setPaint(Color.BLUE);
        g2.draw(bounds);
        g2.setPaint(Color.RED);
        g2.drawString(row + "|" + col, (int) (bounds.getX() + 2), (int) (bounds.getY() + 12));
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
        list.clear();
        list.addAll(leftUnits);
        list.addAll(rightUnits);

        return list;
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
            addUnit(gameObject);
        } else if (gameObject.type == GameObjectType.PROJECTILE) {
            addProjectile(gameObject);
        }
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
        deleteObjects(leftUnits);
        deleteObjects(rightUnits);
        deleteObjects(projectiles);
    }

    public void move(GameObjectOld gameObject) {
        int newRow = (gameObject.y() - GridOld.INDENT_TOP) / GridOld.CELL_SIZE;
        int newCol = (gameObject.x() - GridOld.INDENT_LEFT) / GridOld.CELL_SIZE;

        if (gameObject.x() < GridOld.INDENT_LEFT) {
            //TODO remake
            gameObject.delete = true;
            return;
        }

        if (row != newRow || col != newCol) {
            gameObject.relocate = true;
        }
    }

    public void relocate() {
        relocateObjects(leftUnits);
        relocateObjects(rightUnits);
        relocateObjects(projectiles);
    }

    private void updateObjects(Collection<BasicGameObject> collection) {
        for (BasicGameObject gameObject : collection) {
//            object.update();
        }
    }

    private void deleteObjects(List<BasicGameObject> collection) {
        Iterator<BasicGameObject> it = collection.iterator();
        while (it.hasNext()) {
            if (it.next().delete) {
                it.remove();
            }
        }
    }

    private void relocateObjects(Collection<BasicGameObject> collection) {
        Iterator<BasicGameObject> it = collection.iterator();
        while (it.hasNext()) {
            BasicGameObject gameObject = it.next();
            if (gameObject.relocate) {
                gameObject.relocate = false;
                it.remove();
                grid.add(gameObject);
            }
        }
    }

    public int sizeUnits() {
        return leftUnits.size() + rightUnits.size();
    }
}

