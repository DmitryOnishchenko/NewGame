package com.electdead.newgame.gameobjects.projectiles;

import com.electdead.newgame.assets.Assets;
import com.electdead.newgame.engine.EngineV1;
import com.electdead.newgame.gameobjects.GameObject;
import com.electdead.newgame.gameobjects.GameObjectType;
import com.electdead.newgame.gameobjects.Side;
import com.electdead.newgame.gameobjects.units.Unit;
import com.electdead.newgame.physics.Vector2F;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class Projectile extends GameObject {
    public Vector2F moveDir;
    public int damage;
    public float speed = 400 / EngineV1.UPDATES_PER_SEC;
    private int attackRange = 4;
    public BufferedImage sprite = (BufferedImage) Assets.getProperties("projectiles").get("woodenArrow");

    public Projectile(String name, Side side, GameObjectType type, int damage, float x, float y) {
        super(name, side, type, x, y);
        this.damage = damage;
    }

    @Override
    public void update() {
        float shiftX = speed * moveDir.x;
        pos.x += shiftX;

        float shiftY = speed * moveDir.y;
        pos.y += shiftY;

        getCell().move(this);

        List<GameObject> enemies = (side == Side.LEFT_ARMY) ? getCell().getRightUnits() : getCell().getLeftUnits();
        for (GameObject obj : enemies) {
            Unit enemy = (Unit) obj;
            if (intersects(this, enemy)) {
                enemy.takeDamage(damage);
                delete = true;
                break;
            }
        }

        checkDelete();
    }

    @Override
    public void render(Graphics2D g2, double deltaTime) {
//		g2.drawImage(sprite, (int) pos.x, (int) pos.y, null);
        g2.setPaint(side == Side.LEFT_ARMY ? Color.CYAN : Color.YELLOW);
        Ellipse2D.Float ell = new Ellipse2D.Float();
        ell.setFrameFromCenter(pos.x, pos.y, pos.x + attackRange, pos.y + attackRange);
        g2.fill(ell);
//        g2.fillRect(x() - 2, y() - 2, 4, 4);
    }

    public boolean intersects(Projectile projectile, Unit enemy) {
        if (!enemy.isAlive()) {
            return false;
        }
        double attackRange = projectile.attackRange;
        double enemyHitBoxRadius = enemy.hitBox.width / 2;
        double distance = Vector2F.getDistanceOnScreen(projectile.pos, enemy.pos);

        return distance < (attackRange + enemyHitBoxRadius);
    }
}
