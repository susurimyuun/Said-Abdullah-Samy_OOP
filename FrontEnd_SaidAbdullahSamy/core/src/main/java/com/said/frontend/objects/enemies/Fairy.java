package com.said.frontend.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.said.frontend.objects.Collidable;
import com.said.frontend.objects.Player;
import com.said.frontend.objects.enemies.Enemy;
import com.said.frontend.objects.patterns.entityStrategy.LinearEntityMovement;
import com.said.frontend.objects.patterns.shootingStrategy.FrontShot;

public class Fairy extends Enemy {
    private float shootTimer = 0f;
    private float shootDelay = 0.67f;
    private float shootInterval = 1.4f;
    private int maxShots = 2;
    private int shotsFired = 0;

    public Fairy(String name, int hp){
        super(150,380,24,24, Color.PINK,name,hp,500L);
        setShootingPattern(new FrontShot(150f,10));
        setMovementPattern(new LinearEntityMovement(0f,-40f));

    }
    public Fairy(float x, float y, String name, int hp){

        super(x,y,24,24,Color.PINK,name,hp,500L);
        setShootingPattern(new FrontShot(150f,10));
        setMovementPattern(new LinearEntityMovement(0f,-40f));
    }
    public Fairy(float x, float y, String name, int hp, float shootDelay, float shootInterval, int maxShots) {
        super(x, y, 24, 24, Color.PINK, name, hp, 500L);
        this.shootDelay = shootDelay;
        this.shootInterval = shootInterval;
        this.maxShots = maxShots;
        // TODO (2 points): Inject default strategies FrontShot and LinearEntityMovement
        setShootingPattern(new FrontShot(150f,10));
        setMovementPattern(new LinearEntityMovement(0f,-40f));
    }
    public boolean canShoot(float delta) {
        shootTimer += delta;
        if (shotsFired == 0) {
            if (shootTimer >= shootDelay) {
                shotsFired++;
                shootTimer = 0f;
                return true;
            }
        } else if (shotsFired < maxShots) {
            if (shootTimer >= shootInterval) {
                shotsFired++;
                shootTimer = 0f;
                return true;
            }
        }
        return false;
    }

    public void setShootTiming(float shootDelay, float shootInterval, int maxShots) {
        this.shootDelay = shootDelay;
        this.shootInterval = shootInterval;
        this.maxShots = maxShots;
    }

    public int getShotsFired() { return shotsFired; }
    public float getShootTimer() { return shootTimer; }
    public float getShootDelay() { return shootDelay; }
    public float getShootInterval() { return shootInterval; }
    public int getMaxShots() { return maxShots; }

    @Override
    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is a Player
        if(other instanceof Player){
            System.out.println("Player Touches Fairy");
        }
        // TODO: Print "Player touches fairy"
    }

}
