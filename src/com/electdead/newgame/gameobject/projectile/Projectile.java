package com.electdead.newgame.gameobject.projectile;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.engine.EngineV2;
import com.electdead.newgame.gameobject.GameObjectType;
import com.electdead.newgame.gameobject.Side;
import com.electdead.newgame.gameobjectV2.BasicGameObject;
import com.electdead.newgame.physics.Vector2F;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class Projectile extends BasicGameObject {
    public Vector2F moveDir;
    public int damage;
    private int attackRange = 4;
    public BufferedImage sprite = (BufferedImage) Assets.getProperties("projectile").get("woodenArrow");

    //TODO test
    private Vector2F tail;
    public float speed = 500 / EngineV2.UPDATES_PER_SEC;
    public Vector2F sp;
    private float acc = 0.1f;
    private float baseAcc = 0.46f;
    public BasicGameObject target;

    private boolean finished = false;
    private int delayTimer = 0;
    private int deleteTrigger = 4000 / EngineV2.MS_PER_UPDATE;

    private int length = 16;
    private Vector2F startPoint;
    private int maxDistance = 500;

    public Projectile(String name, Side side, GameObjectType type,
                      int damage, float x, float y, BasicGameObject gameObject) {
//        super(name, side, type, x, y);
        super(name, x, y);
        this.damage = damage;
        tail = currentState.pos.copy();
        zLevel = 0;
        startPoint = currentState.pos.copy();

        //TEST
        this.target = gameObject;
        double distance = Vector2F.getDistanceOnScreen(currentState.pos, gameObject.currentState.pos);
        float speedY = (float) -(distance / 100 * baseAcc);
        float shift = currentState.pos.y - gameObject.currentState.pos.y;
        float k = shift / 36f;
        speedY -= k;

        sp = side == Side.LEFT_ARMY ? new Vector2F(speed, speedY - 0.15f) : new Vector2F(-speed, speedY - 0.15f);
//        if (side == Side.LEFT_ARMY) {
//            System.out.println(pos.y + " | " + gameObject.pos.y);
//        }
    }

    //TODO update projectile
    @Override
    public void updateAction() {
        if (finished) {
            if (delayTimer++ > deleteTrigger) {
                delete = true;
            }
            return;
        }

        tail.x = currentState.pos.x + moveDir.x * length;
        tail.y = currentState.pos.y + moveDir.y * length;

//        if (side == Side.LEFT_ARMY) {
//            System.out.println();
//        }

        //TEST
        float shiftX = sp.x;
        currentState.pos.x += shiftX;
        tail.x += shiftX;

        float shiftY = sp.y;
        currentState.pos.y += shiftY;
        tail.y += shiftY;

        sp.y += acc;

        //TODO maxDistance
        double length = Vector2F.getDistanceOnScreen(startPoint, currentState.pos);
        if (length > maxDistance) {
//            delete = true;
            finished = true;
            return;
        }

        cell.move(this);

//        List<Cell> cells = BattleState.grid.getCellIfIntersectsWith(
//                new Line2D.Float(currentState.pos.x, currentState.pos.y, tail.x, tail.y));
//
//        for (CellOld cell : cells) {
            List<BasicGameObject> list = cell.getAllUnits();

            for (BasicGameObject enemy : list) {
                if (side != enemy.side && intersects(this, enemy)) {
                    enemy.takeDamage(damage);
                    delete = true;
                    break;
                }
            }
//        }

//        checkDelete();
    }

//    @Override
//    public void updatePhysics() {
//
//    }

    //TODO render projectile
    @Override
    public void render(Graphics2D g2, double deltaTime) {
//        g2.setPaint(side == Side.LEFT_ARMY ? Color.CYAN : Color.YELLOW);
        Ellipse2D.Float ell = new Ellipse2D.Float();
        ell.setFrameFromCenter(currentState.pos.x, currentState.pos.y, currentState.pos.x + attackRange, currentState.pos.y + attackRange);
        g2.fill(ell);

        g2.setPaint(Color.BLACK);
        g2.drawLine(x(), y(), (int) tail.x, (int) tail.y);
    }

    public boolean intersects(Projectile projectile, BasicGameObject enemy) {
        if (!enemy.currentState.isAlive()) {
            return false;
        }

        Vector2F center = enemy.currentState.pos;
        float radius = (float) enemy.hitBox.width / 2;
        Vector2F head = projectile.currentState.pos;
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
