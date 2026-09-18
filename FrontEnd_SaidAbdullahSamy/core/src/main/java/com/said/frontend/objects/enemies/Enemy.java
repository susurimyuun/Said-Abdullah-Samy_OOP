package com.said.frontend.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.said.frontend.objects.GameObject;
import com.said.frontend.objects.Player;

public class Enemy extends GameObject {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected long scoreValue;

    public Enemy(String name, int hp){
        super(200,380,24,24,0, Color.PINK);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.scoreValue = 100;
    }
    public Enemy(float x, float y, float width, float height, Color color, String name, int hp, long scoreValue){
        super(x,y,width,height,0,color);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.scoreValue = scoreValue;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getHp(){
        return this.hp;
    }
    public void setHp(int hp){
        this.hp = Math.max(0, hp);
    }
    public int getMaxHp(){
        return this.maxHp;
    }
    public long getScoreValue(){
        return this.scoreValue;
    }
    public void setScoreValue(long scoreValue){
        this.scoreValue = scoreValue;
    }

    public static void main(String[] args) {
        Enemy enemy1 = new Enemy("Reimu Hakurei",50);

        enemy1.takeDamage(10);
    }

    public boolean takeDamage(int damage){
        if (hp == 0){
            return false;
        }
        setHp(getHp()-damage);
        if (hp == 0){
            System.out.println(getName() + " has been defeated!");
            return true;
        }
        else {
            System.out.println(getName() + " took " + damage + " damage! Remaining HP: " + getHp() + "/" + getMaxHp());
            return false;
        }
    }
    public void attack(Player player, int damage){
        System.out.println(getName() + " unleashes bullet barrage on " + player.getName() + "!");
        player.takeDamage(damage);
    }
    public boolean isAlive(){
        if (hp > 0){
            return true;
        }
        else{
            return false;
        }
    }
}
