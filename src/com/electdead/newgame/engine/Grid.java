package com.electdead.newgame.engine;

import com.electdead.newgame.gameobjectV2.BasicGameObject;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Grid {
    private static final int BATTLE_HEIGHT = 500;
    private static final int BATTLE_WIDTH = 1480;
    public static final int CELL_SIZE = 70;
    public static final int ROWS = BATTLE_HEIGHT / CELL_SIZE;
    public static final int COLS = BATTLE_WIDTH / CELL_SIZE;
    public static final int INDENT_TOP = 200;
    public static final int INDENT_LEFT = -60;
    private Cell[][] cells = new Cell[ROWS][COLS];

    public int size;
    private List<BasicGameObject> allObjects = new ArrayList<>(5000);

    public Grid() {
        init();
    }

    public void init() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Rectangle2D.Float bounds = new Rectangle2D.Float(
                        col * CELL_SIZE + INDENT_LEFT, row * CELL_SIZE + INDENT_TOP, CELL_SIZE, CELL_SIZE);
                cells[row][col] = new Cell(this, row, col, bounds);
            }
        }
    }

    public void clear() {
        size = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col].clear();
            }
        }
    }

    public void add(BasicGameObject gameObject) {
        // TODO fix
        int y = gameObject.y() - INDENT_TOP;

        int row = y < 0 ? -1 : y / CELL_SIZE;
        int col = (gameObject.x() - INDENT_LEFT) / CELL_SIZE;

        if (row >= ROWS || col >= COLS ||
            row <= -1 || col <= -1) {
            gameObject.delete = true;
            return;
        }

        Cell cell = cells[row][col];
        gameObject.cell = cell;
        size++;
        cell.add(gameObject);
    }

    public void rebase(BasicGameObject gameObject) {
        int row = (gameObject.y() - INDENT_TOP) / CELL_SIZE;
        int col = (gameObject.x() - INDENT_LEFT) / CELL_SIZE;

        if (row >= ROWS || col >= COLS ||
                row <= -1 || col <= -1) {
            gameObject.delete = true;
            size--;
//            BattleStateSettings.NEED_DELETE = true;
            return;
        }

        Cell cell = cells[row][col];
        gameObject.cell = cell;
        cell.add(gameObject);
    }

    public void update() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col].update();
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

    public List<BasicGameObject> getAllObjects() {
        allObjects.clear();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                allObjects.addAll(cells[row][col].getAllObjects());
            }
        }

        return allObjects;
    }

    public int size() {
        int amount = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                amount += cells[row][col].sizeUnits();
            }
        }

        return amount;
//        return size;
    }

    public void checkDelete() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col].checkDelete();
            }
        }
    }

    public List<Cell> getCellIfIntersectsWith(Shape shape) {
        List<Cell> list = new ArrayList<>(20);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Cell cell = cells[row][col];
                if (shape.intersects(cell.getBounds())) {
                    list.add(cell);
                }
            }
        }

        return list;
    }
}
