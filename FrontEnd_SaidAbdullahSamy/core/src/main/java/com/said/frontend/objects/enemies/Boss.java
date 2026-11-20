package com.said.frontend.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.said.frontend.objects.Collidable;
import com.said.frontend.objects.Player;
import com.said.frontend.objects.patterns.ShootingPattern;
import com.said.frontend.objects.patterns.entityStrategy.FixedMovement;
import com.said.frontend.objects.patterns.shootingStrategy.SpreadShot;
import com.said.frontend.objects.systems.BulletManager;


public class Boss extends Enemy {
    private ShootingPattern shootingPattern;
    private boolean survivesAtZeroHp = false;



    public Boss(String name, int hp){
        super(380,400,64,64, Color.BLUE,name,hp,5000L);

        setShootingPattern(new SpreadShot(200f,3,30f));
        setMovementPattern(new FixedMovement());

    }
    public Boss(float x, float y, String name, int hp){

        super(x,y,64,64,Color.BLUE,name,hp,5000L);
        setShootingPattern(new SpreadShot(200f,3,30f));
        setMovementPattern(new FixedMovement());
    }

    public void shootBullet(BulletManager bulletManager) {
        if (shootingPattern != null && !isDestroyed()) {
            shootingPattern.execute(x + width / 2f - 8f, y, bulletManager, false);
        }
    }
    public ShootingPattern getShootingPattern() {
        return shootingPattern;
    }

    public void setShootingPattern(ShootingPattern shootingPattern) {
        this.shootingPattern = shootingPattern;
    }

    @Override
    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is a Player
        if(other instanceof Player){
            System.out.println("Player Touches Boss");
        }
        // TODO: Print "Player touches fairy"
    }

    public void setLife(int life) {
        this.hp = Math.max(0, life);
        this.maxHp = this.hp;
    }

    @Override
    public boolean takeDamage(int damage) {
        if (!damageable) {
            return false;
        }
        if (!survivesAtZeroHp) {
            return super.takeDamage(damage);
        }
        this.hp = Math.max(0, this.hp - damage);
        System.out.println(name + " took " + damage + " damage! HP: " + this.hp + "/" + this.maxHp);
        return false;
    }

    public boolean isSurvivesAtZeroHp() { return survivesAtZeroHp; }
    public void setSurvivesAtZeroHp(boolean survivesAtZeroHp) { this.survivesAtZeroHp = survivesAtZeroHp; }
}
/* The inheritance that is given tot he Boos/Fairy type is that they can take the methods n attributes from Enemy n GameObject*/
