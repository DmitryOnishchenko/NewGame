package com.electdead.newgame.engine;

import com.electdead.newgame.gameobjects.units.Unit;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Cell {
    public final int row;
    public final int col;

    private Grid grid;
    private LinkedList<Unit> units = new LinkedList<>();
    private Rectangle2D.Float bounds = new Rectangle2D.Float();

    public Cell(Grid grid, int row, int col, Rectangle2D.Float bounds) {
        this.grid = grid;
        this.row = row;
        this.col = col;
        this.bounds = bounds;
    }

    public void update() {
        for (Unit unit : units) {
            unit.update();
        }
    }

    public void render(Graphics2D g2, double deltaTime) {
        g2.setPaint(Color.BLUE);
        g2.draw(bounds);

        for (Unit unit : units) {
            unit.render(g2, deltaTime);
        }
    }

    public void clear() {
        units.clear();
    }

    public List<Unit> getAllObjects() {
        return units;
    }

    public void add(Unit unit) {
        System.out.println("Add unit at " + row + "|" + col);
        units.add(unit);
    }

    public void checkDelete() {
        ListIterator<Unit> it = units.listIterator();
        while (it.hasNext()) {
            if (it.next().delete) {
                it.remove();
            }
        }
    }

    public void move(Unit unit) {
        int newRow = (int) unit.y() / grid.CELL_SIZE;
        int newCol = (int) unit.x() / grid.CELL_SIZE;

        if (row != newRow || col != newCol) {
            
        }
    }
}
