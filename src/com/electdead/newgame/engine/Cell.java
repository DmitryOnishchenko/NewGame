package com.electdead.newgame.engine;

import com.electdead.newgame.gameobjects.units.Unit;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
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
        Iterator<Unit> it = units.iterator();
        while (it.hasNext()) {
            Unit unit = it.next();
            unit.update();
        }
    }

    public void render(Graphics2D g2, double deltaTime) {
        g2.setPaint(Color.BLUE);
        g2.draw(bounds);
        g2.setPaint(Color.RED);
        g2.drawString(row + "|" + col, (int)(bounds.getX() + 2),(int)(bounds.getY() + 12));

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
//        System.out.println(unit.name + " added at " + row + "|" + col);
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

        int newRow = (unit.y() - Grid.INDENT_TOP) / Grid.CELL_SIZE;
        int newCol = (unit.x() - Grid.INDENT_LEFT) / Grid.CELL_SIZE;

        if (unit.x() < Grid.INDENT_LEFT) {
            //TODO maybe bad-bad-bad!
//            System.out.println(unit.name + " removed from " + row + "|" + col);
            unit.delete = true;
            return;
        }

        if (row != newRow || col != newCol) {
//            System.out.println(unit.name + " need to relocate");
            unit.relocate = true;
        }
    }

    public void relocate() {
        Iterator<Unit> it = units.iterator();
        while (it.hasNext()) {
            Unit unit = it.next();
            if (unit.relocate) {
                unit.relocate = false;
                it.remove();
                grid.add(unit);
            }
        }
    }
}
