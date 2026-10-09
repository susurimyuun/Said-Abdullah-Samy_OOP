package com.said.frontend.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.said.frontend.objects.bullets.Bullet;
import com.said.frontend.objects.bullets.BulletType;
import com.said.frontend.objects.items.Item;
import com.said.frontend.objects.enemies.Enemy;
import com.said.frontend.objects.items.ItemType;
import com.said.frontend.objects.systems.AssetManager;
import com.said.frontend.objects.systems.EntityFactory;

import java.time.temporal.Temporal;

public class Player extends GameObject {
    private String name;
    private int hp;
    private int power;
    private int spellCards;
    private long score;
    private int currentDir;

    public Player(String name, int hp, int power, int spellCards){
        super(280,40,32,48,200f, Color.RED);
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
        super(x,y,32,48,200f, Color.RED);
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

    //PreCS M4
    public Bullet shootBullet() {
        int damage = 10 + power;
        System.out.println(name + " shoots bullet dealing " + damage + " DMG!");
        // TODO: Return a Bullet using EntityFactory
        // with the same x and y formulas as in the previous implementation.
        // TODO: return a new Bullet positioned at the top-center of the Player
        // (x + width/2 - 4, y + height), with BulletType.AMULET as its type,
        // and the damage calculated above
        return new Bullet(x+width/2-4,y+height, BulletType.AMULET,damage);
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
        ItemType type = item.getItemTypeEnum();
        if (item.isDestroyed()) return;
        if (type != null) {
            switch (type) {
                case POWER -> {
                    // 1. Increase power by type.getPowerBonus() via this.power
                    this.power += type.getPowerBonus();
                    // 2. Add score by item.getScoreValue() via addScore() (addScore() already automatically prints "gained X pts!")
                    addScore(item.getScoreValue());
                    // 3. Print: [name] collected POWER item! Power increased to [power]
                    System.out.println(getName() + "Collected POWER item! Power increased to " + getPower());


                }
                case POINT -> {
                    // 1. Add score by item.getScoreValue() via addScore()
                    addScore(item.getScoreValue());
                    // 2. Print: [name] collected POINT item!
                    System.out.println(getName() + "Collected POINT Item! ");
                }
                case BOMB -> {
                    // 1. Increase spellCards by 1
                    this.spellCards += 1;
                    // 2. Add score by item.getScoreValue() via addScore()
                    addScore(item.getScoreValue());
                    // 3. Print: [name] collected BOMB item! SpellCards: [spellCards]
                    System.out.println(getName() + "Collected BOMB item! SpellCards: " + spellCards);
                }
                case LIFE -> {
                    // 1. Increase hp by 20
                    this.hp += 20;
                    // 2. Add score by item.getScoreValue() via addScore()
                    addScore(item.getScoreValue());
                    // 3. Print: [name] collected LIFE item! HP: [hp]
                    System.out.println(getName() + "Collected LIFE Item! HP : " + hp);
                }
            }
            item.destroy();

        }
        else {
            addScore(item.getScoreValue());
            System.out.println(name + " collected " + item.getItemType() + "!");
        }


    }

    @Override
    public void update(float delta) {
        super.update(delta);
        float dx = 0f;
        if (Gdx.input != null) {
            if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
                y += speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                y -= speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                x -= speed * delta;
                // TODO 3: Adjust dx to match the direction.
                // (If you move left, what should happen to dx?)
                dx = x;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                x += speed * delta;
                // TODO 4: Adjust dx to match the direction.
                // (If you move right, what should happen to dx?)
                dx = x;
            }
        }
        updateAnimationState(dx);

    }

    public void updateAnimationState(float dx) {
        AssetManager assets = AssetManager.getInstance();
        if (dx < 0) {
            // TODO:
            // 1. Only make the following changes if currentDir is not -1.
            // 2. Set currentDir to -1.
            // 3. Retrieve the "player_left" animation using assets.getAnimation(...).
            //    Store it in a local variable of type Animation<TextureRegion> named anim.
            // 4. If anim is not null, assign it using setAnimation(...).
            if (currentDir != -1){
                this.currentDir = -1;
                assets.getAnimation("player_left");
                Animation<TextureRegion> anim = assets.getAnimation("player_left");
                if (anim != null){
                    setAnimation(anim);
                }

            }
        } else if (dx > 0) {
            // TODO:
            // 1. Only make the following changes if currentDir is not 1.
            // 2. Set currentDir to 1.
            // 3. Retrieve the "player_right" animation using assets.getAnimation(...).
            //    Store it in a local variable of type Animation<TextureRegion> named anim.
            // 4. If anim is not null, assign it using setAnimation(...).
            if (currentDir != 1){
                this.currentDir = 1;
                Animation<TextureRegion> anim = assets.getAnimation("player_right");
                if (anim != null){
                    setAnimation(anim);
                }
            }
        } else {
            // TODO:
            // 1. Only make the following changes if currentDir is not 0.
            // 2. Set currentDir to 0.
            // 3. Retrieve the "player_idle" animation using assets.getAnimation(...).
            //    Store it in a local variable of type Animation<TextureRegion> named anim.
            // 4. If anim is not null, assign it using setAnimation(...).
            if ( currentDir != 0){
                this.currentDir = 0;
                Animation<TextureRegion>anim = assets.getAnimation("player_idle");
                if (anim != null){
                    setAnimation(anim);
                }
            }
        }
    }

    @Override
    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is an Item
        if (other instanceof Item){
            System.out.println("Player touches items");
            collectItem((Item) other);
        }
        // TODO: Print "Player touches items" then call collectItem((Item) other)
    }




}
