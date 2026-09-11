package com.said.frontend;

import java.awt.*;

import static java.awt.Color.RED;

public class Player extends GameObject {
    private String name;
    private int hp;
    private int power;
    private int spellCards;
    private long score;

    public Player(String name, int hp, int power, int spellCards){
        super(280,40,32,32,0, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;

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
    public int getPower(){
        return this.power;
    }
    public void setPower(int power){
        this.power = power;
    }
    public int getSpellCards(){
        return this.spellCards;
    }
    public void setSpellCards(int spellCards){
        this.spellCards = spellCards;
    }
    public long getScore(){
        return this.score;
    }

    public Player(float x, float y, String name, int hp, int power, int spellCards){
        super(x,y,32,32,0, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;

    }
    public static void main(String[] args) {
        Player player1 = new Player("Reimu Hakurei",100,15,3);
        player1.takeDamage(10);
    }

    public void takeDamage(int damage){
        setHp(getHp() - damage);
        System.out.println(getName() + " took " + damage + " damage! Remaining HP: " + getHp());

        if (hp == 0){
            System.out.println(getName() + " has been defeated!");
        }
    }
    public void shoot (Enemy target){
        int damage = 10 + getPower();
        System.out.println(getName() + " shoots " + target.getName() + " dealing " + damage + " DMG!");
        target.takeDamage(damage);
    }
    public boolean isAlive(){
        if (hp > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public void addScore(long points) {
        // TODO: Add the value to the player's score if points is greater than 0.
        if (points > 0) {
            this.score += points;
            System.out.println(getName() + " gained " + points + " pts! Total Score: " + this.score);
        }
    }
    public void collectItem(Item item) {
        System.out.println(getName() + " collected " + item.getItemType() + "!");
        if (item.getScoreValue() > 0) {
            addScore(item.getScoreValue());
        }
    }


}
