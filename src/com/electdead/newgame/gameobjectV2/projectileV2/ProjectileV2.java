package com.electdead.newgame.gameobjectV2.projectileV2;

import com.electdead.newgame.engine.EngineV2;
import com.electdead.newgame.gameobject.GameObjectType;
import com.electdead.newgame.gameobject.Side;
import com.electdead.newgame.gameobjectV2.BasicGameObject;
import com.electdead.newgame.physics.Vector2F;

import java.awt.*;

public class ProjectileV2 extends BasicGameObject {
    private final int damage;
    private float speed = 600 / EngineV2.UPDATES_PER_SEC;
    private Vector2F tail;
    private int length = side == Side.LEFT_ARMY ? -16 : 16;

    private boolean finished = false;
    private int delayTimer = 0;
    private int deleteTrigger = 4000 / EngineV2.MS_PER_UPDATE;

    private Vector2F startPoint;
    private int maxDistance;
    private float acc = 0;

    public ProjectileV2(String name, BasicGameObject gameObject) {
        super(name, gameObject.x(), gameObject.y());

        this.side = gameObject.side;
        this.type = GameObjectType.PROJECTILE;
        this.damage = gameObject.currentState.damage;
        this.zLevel = 1;

        Vector2F newDir = gameObject.target.currentState.pos.copy();
        newDir.sub(gameObject.currentState.pos);
        newDir.normalize();
        this.currentState.moveDir = newDir;

        tail = new Vector2F(x() + currentState.moveDir.x *length, y() + currentState.moveDir.y * length);

        startPoint = currentState.pos.copy();
//        maxDistance = (int) gameObject.pModel.getSearchRange();
        maxDistance = (int) Vector2F.getDistanceOnScreen(startPoint, gameObject.target.currentState.pos) + 50;
    }

    @Override
    public void updateAction() {
        //TODO maxDistance
        double distance = Vector2F.getDistanceOnScreen(startPoint, currentState.pos);
        if (distance > maxDistance) {
            finished = true;
            zLevel = 0;
        }

        if (finished) {
            if (delayTimer++ > deleteTrigger) {
                delete = true;
            }
            return;
        }

        float shiftX = speed * currentState.moveDir.x;
        currentState.pos.x += shiftX;

        float shiftY = speed * currentState.moveDir.y;
        currentState.pos.y += shiftY;

        tail.x = currentState.pos.x + currentState.moveDir.x * length;
        tail.y = currentState.pos.y + currentState.moveDir.y * length;

        cell.move(this);

        java.util.List<BasicGameObject> list = cell.getAllUnits();

        for (BasicGameObject enemy : list) {
            if (side != enemy.side && type != enemy.type && intersects(this, enemy)) {
                enemy.takeDamage(damage);
                delete = true;
                break;
            }
        }
    }

    @Override
    public void render(Graphics2D g2, double deltaTime) {
        g2.setPaint(Color.BLACK);
        int x = x();
        int y = y();

        //TODO test
        double distance = Vector2F.getDistanceOnScreen(startPoint, currentState.pos);
        if (distance < (maxDistance - 50) / 2) {
            acc += 0.33f;
        } else {
            acc -= 0.33f;
        }

        if (finished) {
            acc = 0;
        }

        g2.drawLine(x, y - 12 - (int) acc, (int) (tail.x), (int) (tail.y) - 12 - (int) acc);
    }

    public boolean intersects(ProjectileV2 projectileV2, BasicGameObject enemy) {
        if (!enemy.currentState.isAlive()) {
            // TODO add multiarrow hit
//            int enemySprite = enemy.action.animation.currentSprite;
//            if (enemySprite > 3 || enemySprite == 0) {
                return false;
//            }
        }

        Vector2F center = enemy.currentState.pos;
        float radius = (float) enemy.hitBox.width / 2;
        Vector2F head = projectileV2.currentState.pos.copy();
        Vector2F tail = projectileV2.tail.copy();

        //TODO test this
        head.x -= currentState.moveDir.x * length;

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
