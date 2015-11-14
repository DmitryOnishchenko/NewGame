package com.electdead.newgame.engine;

import com.electdead.newgame.gameobject.GameObjectOld;
import com.electdead.newgame.gameobject.GameObjectType;
import com.electdead.newgame.gameobject.Side;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

public class Cell {
    private final int row;
    private final int col;
    private Grid grid;
    private Rectangle2D.Float bounds = new Rectangle2D.Float();

    private List<GameObjectOld> leftUnits = new FastRemoveArrayList<>(100);
    private List<GameObjectOld> rightUnits = new FastRemoveArrayList<>(100);
    private List<GameObjectOld> projectiles = new FastRemoveArrayList<>(100);

    private List<GameObjectOld> list = new FastRemoveArrayList<>(1000);

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

    public List<GameObjectOld> getAllObjects() {
        List<GameObjectOld> list = getAllUnits();
        list.addAll(projectiles);

        return list;
    }

    public List<GameObjectOld> getAllUnits() {
        list.clear();
        list.addAll(leftUnits);
        list.addAll(rightUnits);

        return list;
    }

    public List<GameObjectOld> getLeftUnits() {
        return leftUnits;
    }

    public List<GameObjectOld> getRightUnits() {
        return rightUnits;
    }

    public List<GameObjectOld> getProjectiles() {
        return projectiles;
    }

    public void add(GameObjectOld gameObject) {
        if (gameObject.type == GameObjectType.UNIT) {
            addUnit(gameObject);
        } else if (gameObject.type == GameObjectType.PROJECTILE) {
            addProjectile(gameObject);
        }
    }

    private void addUnit(GameObjectOld gameObject) {
        if (gameObject.side == Side.LEFT_ARMY) {
            leftUnits.add(gameObject);
        } else {
            rightUnits.add(gameObject);
        }
    }

    private void addProjectile(GameObjectOld gameObject) {
        projectiles.add(gameObject);
    }

    public void checkDelete() {
        deleteObjects(leftUnits);
        deleteObjects(rightUnits);
        deleteObjects(projectiles);
    }

    public void move(GameObjectOld gameObject) {
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

    private void updateObjects(Collection<GameObjectOld> collection) {
        for (GameObjectOld gameObject : collection) {
            gameObject.update();
        }
    }

    private void deleteObjects(List<GameObjectOld> collection) {
        Iterator<GameObjectOld> it = collection.iterator();
        while (it.hasNext()) {
            if (it.next().delete) {
                it.remove();
            }
        }
    }

    private void relocateObjects(Collection<GameObjectOld> collection) {
        Iterator<GameObjectOld> it = collection.iterator();
        while (it.hasNext()) {
            GameObjectOld gameObject = it.next();
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

class FastRemoveArrayList<E> extends ArrayList<E> {
    public FastRemoveArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public E remove(int index) {
        E item = get(size()-1);
        set(index, item);
        return super.remove(size()-1);
    }
}
