package com.said.frontend.objects.bullets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.said.frontend.objects.Collidable;
import com.said.frontend.objects.GameObject;
import com.said.frontend.objects.enemies.Enemy;

public class Bullet extends GameObject {
    private BulletType bulletType ;
    private int damage;
    // TODO: Declare the following private attributes:
    // 1. float vx and vy (2D velocity vector components)
    // 2. float angle initialized to 90f (visual rotation angle in degrees)
    // 3. boolean isPlayerBullet (flag indicating whether this bullet belongs to the player or an enemy)
    // 4. boolean grazed (flag indicating whether this bullet has already been grazed by the player)
    private float vx, vy;
    private float angle = 90f;
    private boolean isPlayerBullet, grazed;

    public Bullet(float x, float y, BulletType bulletType, int damage) {
        super(x, y, 8, 16, 400f, Color.YELLOW);
        // TODO: initialize bulletType and damage from the parameters
        this.bulletType = bulletType;
        this.damage = damage;
        this.vx = 0;
        this.vy = 400f;
        this.angle = 0f;
        this.isPlayerBullet = true;
        this.grazed = false;
    }

    public Bullet(float x, float y, float speed, BulletType bulletType, int damage) {
        super(x, y, 8, 16, speed, Color.YELLOW);
        // TODO: initialize bulletType and damage from the parameters
        this.bulletType = bulletType;
        this.damage = damage;
        this.vx = 0;
        this.vy = speed;
        if (speed < 0){
            this.angle = 270f;
        }
        else{
            this.angle = 90f;
        }
        this.isPlayerBullet = true;
        this.grazed = false;
    }

    @Override
    public void update(float delta) {
        // TODO: increase the y position by speed * delta (bullet moves upward)
        super.update(delta);
        y += speed * delta;
        this.x += this.vx * delta;
        this.y += this.vy * delta;
    }

    public BulletType getBulletType() {
        return bulletType;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Enemy enemy) {
            // 1. Display a message indicating that the Bullet hit the Enemy in the format:
            //    Bullet hit [EnemyName] for [damage] DMG!
            System.out.println("Bullet hit " + enemy.getName() + " for " + getDamage() + " DMG!");

            // 2. Call the Enemy's takeDamage() method with this Bullet's damage.
            enemy.takeDamage(getDamage());

            // 3. Destroy the bullet after hitting the Enemy, regardless of the result
            //    (whether the enemy is defeated or still alive), because one bullet
            //    can only hit one target.
            destroy();
        }
    }
    // Pool Re-initialization for the Object Pool Pattern
    public void init(float x, float y, float vx, float vy, BulletType bulletType, int damage, boolean isPlayerBullet) {
        // TODO:
        // 1. Update position (x, y) and velocity vector components (vx, vy) from the argument parameters
        // 2. Calculate the visual rotation angle using Math.atan2 as explained above, then convert it to degrees
        // 3. Update speed by calculating the resultant length of both velocity vectors using Math.hypot as explained above
        // 4. Save the bullet's identity attributes (bulletType, damage, and isPlayerBullet) from the parameters
        // 5. Reactivate this bullet and reset its animation state time from 0
        // 6. Make sure the grazed status is reset to inactive so this recycled bullet can be grazed again
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.angle = (float) Math.toDegrees(Math.atan2(vy,vx)) ;
        this.speed = (float) Math.hypot(vx, vy);

        this.bulletType = bulletType;
        this.damage = damage;
        this.isPlayerBullet = isPlayerBullet;

        this.active = true;
        this.stateTime = 0.0f;

        this.grazed = false;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (batch != null && active) {
            if (sprite != null) {
                // TODO: Draw the bullet sprite using batch.draw() with the following parameters:
                // sprite, position (x, y), rotation pivot origin at center (width / 2f, height / 2f),
                // dimensions (width, height), scale (1f, 1f), and rotation angle (angle - 90f)
                batch.draw(sprite, x, y, width / 2f, height / 2f, width, height, 1f, 1f, angle - 90f);
            } else {
                super.render(batch);
            }
        }
    }
    public boolean isPlayerBullet() {
        return isPlayerBullet;
    }

    public float getVx() {
        return vx;
    }

    public float getVy() {
        return vy;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public boolean hasBeenGrazed() {
        return grazed;
    }

    public void setGrazed(boolean grazed) {
        this.grazed = grazed;
    }




}
