package com.electdead.newgame.engine;

import com.electdead.newgame.gameobjects.GameObject;
import com.electdead.newgame.gameobjects.GameObjectType;
import com.electdead.newgame.gameobjects.Side;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

public class Cell {
    private final int row;
    private final int col;
    private Grid grid;
    private Rectangle2D.Float bounds = new Rectangle2D.Float();

    private List<GameObject> leftUnits = new LinkedList<>();
    private List<GameObject> rightUnits = new LinkedList<>();
    private List<GameObject> projectiles = new LinkedList<>();

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

    public List<GameObject> getAllObjects() {
        List<GameObject> list = getAllUnits();
        list.addAll(projectiles);

        return list;
    }

    public List<GameObject> getAllUnits() {
        List<GameObject> list = new LinkedList<>();
        list.addAll(leftUnits);
        list.addAll(rightUnits);

        return list;
    }

    public List<GameObject> getLeftUnits() {
        return leftUnits;
    }

    public List<GameObject> getRightUnits() {
        return rightUnits;
    }

    public List<GameObject> getProjectiles() {
        return projectiles;
    }

    public void add(GameObject gameObject) {
        if (gameObject.type == GameObjectType.UNIT) {
            addUnit(gameObject);
        } else if (gameObject.type == GameObjectType.PROJECTILE) {
            addProjectile(gameObject);
        }
    }

    private void addUnit(GameObject gameObject) {
        if (gameObject.side == Side.LEFT_ARMY) {
            leftUnits.add(gameObject);
        } else {
            rightUnits.add(gameObject);
        }
    }

    private void addProjectile(GameObject gameObject) {
        projectiles.add(gameObject);
    }

    public void checkDelete() {
        deleteObjects(leftUnits);
        deleteObjects(rightUnits);
        deleteObjects(projectiles);
    }

    public void move(GameObject gameObject) {
        int newRow = (gameObject.y() - Grid.INDENT_TOP) / Grid.CELL_SIZE;
        int newCol = (gameObject.x() - Grid.INDENT_LEFT) / Grid.CELL_SIZE;

        if (gameObject.x() < Grid.INDENT_LEFT) {
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

    private void updateObjects(Collection<GameObject> collection) {
        for (GameObject gameObject : collection) {
            gameObject.update();
        }
    }

    private void deleteObjects(List<GameObject> collection) {
        ListIterator<GameObject> it = collection.listIterator();
        while (it.hasNext()) {
            if (it.next().delete) {
                it.remove();
            }
        }
    }

    private void relocateObjects(Collection<GameObject> collection) {
        Iterator<GameObject> it = collection.iterator();
        while (it.hasNext()) {
            GameObject gameObject = it.next();
            if (gameObject.relocate) {
                gameObject.relocate = false;
                it.remove();
                grid.add(gameObject);
            }
        }
    }
}
