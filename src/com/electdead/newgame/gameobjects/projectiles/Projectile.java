package com.electdead.newgame.gameobjects.projectiles;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.engine.Cell;
import com.electdead.newgame.engine.EngineV1;
import com.electdead.newgame.gameobjects.GameObject;
import com.electdead.newgame.gameobjects.GameObjectType;
import com.electdead.newgame.gameobjects.Side;
import com.electdead.newgame.gameobjects.units.Unit;
import com.electdead.newgame.gamestate.DevGameState;
import com.electdead.newgame.physics.Vector2F;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class Projectile extends GameObject {
    public Vector2F moveDir;
    public int damage;
    private int attackRange = 2;
    public BufferedImage sprite = (BufferedImage) Assets.getProperties("projectiles").get("woodenArrow");

    //TODO test
    private Vector2F tail;
    public float speed = 500 / EngineV1.UPDATES_PER_SEC;
    public Vector2F sp;
    private float acc = 0.1f;
    private float baseLeftY = -1.9f;
    public Unit target;

    private boolean finished = false;
    private int delayTimer = 0;
    private int deleteTrigger = 4000 / EngineV1.MS_PER_UPDATE;

    private int length = 16;
    private Vector2F startPoint;
    private int maxDistance = 500;

    public Projectile(String name, Side side, GameObjectType type, int damage, float x, float y, Unit target) {
        super(name, side, type, x, y);
        this.damage = damage;
        tail = pos.copy();
        zLevel = 0;
        startPoint = pos.copy();

        //TEST
        float ySpeed = 0f;
        float shift = pos.y - target.pos.y;
        float k = shift / 40.57f;
        baseLeftY -= k;

        sp = side == Side.LEFT_ARMY ? new Vector2F(speed, baseLeftY) : new Vector2F(speed, -2.15f);
        if (side == Side.LEFT_ARMY) {
            System.out.println(Vector2F.getDistanceOnScreen(pos, target.pos));
        }
    }

    //TODO update projectiles
    @Override
    public void update() {
        if (finished) {
            if (delayTimer++ > deleteTrigger) {
                delete = true;
            }
            return;
        }

        tail.x = pos.x + moveDir.x * length;
        tail.y = pos.y + moveDir.y * length;

        //TEST
        float shiftX = sp.x;
        pos.x += shiftX;
        tail.x += shiftX;

        float shiftY = sp.y;
        pos.y += shiftY;
        tail.y += shiftY;

        sp.y += acc;

        //TODO maxDistance
        double length = Vector2F.getDistanceOnScreen(startPoint, pos);
        if (length > maxDistance) {
//            delete = true;
            finished = true;
            return;
        }

        getCell().move(this);

        List<Cell> cells = DevGameState.grid.getCellIfIntersectsWith(new Line2D.Float(pos.x, pos.y, tail.x, tail.y));

        for (Cell cell : cells) {
            List<GameObject> list;
            if (side == Side.LEFT_ARMY) {
                list = cell.getRightUnits();
            } else list = cell.getLeftUnits();

            for (GameObject obj : list) {
                Unit enemy = (Unit) obj;
                if (intersects(this, enemy)) {
                    enemy.takeDamage(damage);
//                    delete = true;
                    delete = true;
                    break;
                }
            }
        }

        checkDelete();
    }

    //TODO render projectiles
    @Override
    public void render(Graphics2D g2, double deltaTime) {
//        g2.setPaint(side == Side.LEFT_ARMY ? Color.CYAN : Color.YELLOW);
//        Ellipse2D.Float ell = new Ellipse2D.Float();
//        ell.setFrameFromCenter(pos.x, pos.y, pos.x + attackRange, pos.y + attackRange);
//        g2.fill(ell);
        g2.setPaint(Color.BLACK);
        g2.drawLine(x(), y(), (int) tail.x, (int) tail.y);
    }

    public boolean intersects(Projectile projectile, Unit enemy) {
        if (!enemy.isAlive()) {
            return false;
        }

        Vector2F center = enemy.pos;
        float radius = (float) enemy.hitBox.width / 2;
        Vector2F head = projectile.pos;
        Vector2F tail = projectile.tail;

        float x01 = head.x - center.x;
        float y01 = head.y - center.y;
        float x02 = tail.x - center.x;
        float y02 = tail.y - center.y;

        float dx = x02 - x01;
        float dy = y02 - y01;

        float a = dx * dx + dy * dy;
        float b = 2.0f * (x01 * dx + y01 * dy);
        float c = x01 * x01 + y01 * y01 - radius * radius;

        if (-b < 0) return (c < 0);
        if (-b < (2.0f * a)) return (4.0f * a * c - b * b < 0);
        return (a + b + c < 0);
    }
}
