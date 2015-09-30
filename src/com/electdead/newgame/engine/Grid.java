package com.electdead.newgame.engine;

import com.electdead.newgame.gameobjects.units.Unit;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

public class Grid {
    public static final int ROWS = 6;
    public static final int COLS = 13;
    public static final int CELL_SIZE = 100;
    public static final int INDENT_TOP = 100;
    public static final int INDENT_LEFT = 0;
    private Cell[][] cells = new Cell[ROWS][COLS];

    public Grid() {
        init();
    }

    public void init() {
        System.out.println("Init grid: create cells");
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Rectangle2D.Float bounds = new Rectangle2D.Float(
                        col * CELL_SIZE + INDENT_LEFT, row * CELL_SIZE + INDENT_TOP, CELL_SIZE, CELL_SIZE);
                cells[row][col] = new Cell(this, row, col, bounds);
                System.out.print("[" + row + " " + col + "] ");
            }
            System.out.println();
        }
    }

    public void clear() {
        System.out.println("Clear grid: delete cells");
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col].clear();
            }
        }
    }

    public void add(Unit unit) {
        int row = (unit.y() - INDENT_TOP) / CELL_SIZE;
        int col = unit.x() / CELL_SIZE;

        if (row >= ROWS || col >= COLS ||
            row <= -1 || col <= -1) {
            System.out.println(unit.name + " removed from " + row + "|" + col);
            return;
        }

        Cell cell = cells[row][col];
        cell.add(unit);
        unit.setCell(cell);
    }

    public void update() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col].update();
            }
        }
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col].relocate();
            }
        }
    }

    public void render(Graphics2D g2, double deltaTime) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col].render(g2, deltaTime);
            }
        }
    }

    public List<Unit> getAllObjects() {
        List<Unit> list = new LinkedList<>();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                list.addAll(cells[row][col].getAllObjects());
            }
        }

        return list;
    }

    public int amountOfUnits() {
        int amount = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                amount += cells[row][col].getAllObjects().size();
            }
        }

        return amount;
    }

    public void checkDelete() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col].checkDelete();
            }
        }
    }
}
