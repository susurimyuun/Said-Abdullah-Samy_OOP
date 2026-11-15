package com.said.frontend.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.said.frontend.objects.items.Item;
import com.said.frontend.objects.enemies.Enemy;
import com.said.frontend.objects.items.ItemType;
import com.said.frontend.objects.patterns.ShootingPattern;
import com.said.frontend.objects.patterns.bulletStrategy.HomingBulletMovement;
import com.said.frontend.objects.patterns.entityStrategy.FixedMovement;
import com.said.frontend.objects.patterns.shootingStrategy.CompositeShootingPattern;
import com.said.frontend.objects.patterns.shootingStrategy.FrontShot;
import com.said.frontend.objects.patterns.shootingStrategy.SideShot;
import com.said.frontend.objects.patterns.shootingStrategy.SpreadShot;
import com.said.frontend.objects.systems.AssetManager;
import com.said.frontend.objects.systems.BulletManager;
import com.said.frontend.observers.GameObserver;

import java.util.ArrayList;
import java.util.List;

public class Player extends EntityShooter {
    private String name;
    private int hp;
    private int lives = 3;
    private int power;
    private int spellCards;
    private long score;
    private int currentDir;
    // TODO: Declare the following private attributes:
    // 1. int grazeCount with an initial value of 0 (stores the number of grazed bullets)
    int grazeCount = 0;
    // 2. boolean focused with an initial value of false (indicates whether the player is holding Shift / Focus Mode)
    boolean focused = false;
    // 3. float shootTimer with an initial value of 0.1f (timer tracking the delay between shots)
    // 4. float shootCooldown with an initial value of 0.1f (0.1s cooldown threshold / 10 shots per second while holding Z)
    float shootCooldown = 5f/60f;
    float shootTimer = shootCooldown;
    // 5. float invulnerableTimer with an initial value of 0f (timer tracking invulnerability duration after respawn / bomb)
    float invulnerableTimer = 0f;
    private float homingCooldown = 30f / 60f; // 30 frames (0.5s) or 15 frames (0.25s)
    private float homingShootTimer = homingCooldown;

    // TODO: Declare a private field named shootingPattern of type ShootingPattern.
    //       No need to initialize it right away.
    private ShootingPattern shootingPattern;
    private ShootingPattern mainShootingPattern;
    private ShootingPattern sideShootingPattern;
    // ...
    // TODO: Declare a private attribute List<GameObserver> observers and initialize it with an empty ArrayList
    private List<GameObserver> observers = new ArrayList<>();

    public void registerObserver(GameObserver observer) {
        // TODO:
        // 1. Check whether observer is not null AND is not already in the observers list (!observers.contains(observer)).
        // 2. If valid:
        //    - Add observer to the observers list.
        //    - Broadcast all current initial player states to that observer:
        //      * observer.onScoreChanged(score);
        //      * observer.onHpChanged(hp);
        //      * observer.onLivesChanged(lives);
        //      * observer.onSpellCardsChanged(spellCards);
        //      * observer.onPowerChanged(power);
        //      * observer.onGrazeChanged(grazeCount);
        if(observer != null && !observers.contains(observer)){
            observers.add(observer);
            observer.onScoreChanged(score);
            observer.onHpChanged(hp);
            observer.onLivesChanged(lives);
            observer.onSpellCardsChanged(spellCards);
            observer.onPowerChanged(power);
            observer.onGrazeChanged(grazeCount);
        }
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    // Notification Broadcast Methods to All Observers (4 points)
    // TODO: Create the following 6 private void methods that LOOP THROUGH ALL ELEMENTS IN 'observers'
    //       and invoke the corresponding callback method:
    private void notifyHpChanged() {
        for (GameObserver obs : observers) {
            obs.onHpChanged(this.hp);
        }
    }

    private void notifyScoreChanged() {
        for (GameObserver obs : observers) {
            obs.onScoreChanged(this.score);
        }
    }
    private void notifySpellCardsChanged() {
        for (GameObserver obs : observers) {
            obs.onScoreChanged(this.spellCards);
        }
    }
    private void notifyPowerChanged() {
        for (GameObserver obs : observers) {
            obs.onScoreChanged(this.power);
        }
    }
    private void notifyGrazeChanged() {
        for (GameObserver obs : observers) {
            obs.onScoreChanged(this.grazeCount);
        }
    }
    private void notifyLivesChanged() {
        for (GameObserver obs : observers) {
            obs.onScoreChanged(this.lives);
        }
    }
    // 2. notifyHpChanged()         -> calls obs.onHpChanged(hp)
    // 3. notifyLivesChanged()      -> calls obs.onLivesChanged(lives) (Touhou Life System)
    // 4. notifySpellCardsChanged() -> calls obs.onSpellCardsChanged(spellCards)
    // 5. notifyPowerChanged()      -> calls obs.onPowerChanged(power)
    // 6. notifyGrazeChanged()      -> calls obs.onGrazeChanged(grazeCount)

    public Player(String name, int hp, int power, int spellCards){
        super(280,40,32,48,240f, Color.RED);
        setMovementPattern(new FixedMovement());
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        updatePowerStrategy();
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
        notifyLivesChanged();
        notifyHpChanged();
        notifyPowerChanged();
        notifySpellCardsChanged();
    }
    public int getPower(){
        return this.power;
    }
    public void setPower(int power){

        this.power = power;
        notifyLivesChanged();
        notifyHpChanged();

        updatePowerStrategy();
        notifyPowerChanged();
        notifyPowerChanged();
        notifySpellCardsChanged();
    }
    public int getSpellCards(){
        return this.spellCards;
    }
    public void setSpellCards(int spellCards){

        this.spellCards = spellCards;
        notifyLivesChanged();
        notifyHpChanged();
        notifyPowerChanged();
        notifySpellCardsChanged();
    }
    public long getScore(){
        return this.score;
    }

    public Player(float x, float y, String name, int hp, int power, int spellCards){
        super(x,y,32,48,240f, Color.RED);
        setMovementPattern(new FixedMovement());

        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        updatePowerStrategy();
    }
    public static void main(String[] args) {
        Player player1 = new Player("Reimu Hakurei",100,15,3);
        player1.takeDamage(10);
    }

    public void shoot (Enemy target){
        int damage = 10 + getPower();
        System.out.println(getName() + " shoots " + target.getName() + " dealing " + damage + " DMG!");
        target.takeDamage(damage);
    }

    //PreCS M4
    /*public Bullet shootBullet() {
        AssetManager.getInstance().playSFX("shoot");
        int damage = 10 + power;
        System.out.println(name + " shoots bullet dealing " + damage + " DMG!");
        // TODO: Return a Bullet using EntityFactory
        return EntityFactory.createPlayerBullet(x+width/2-4,y+height,damage);
        // with the same x and y formulas as in the previous implementation.
        // TODO: return a new Bullet positioned at the top-center of the Player
        // (x + width/2 - 4, y + height), with BulletType.AMULET as its type,
        // and the damage calculated above
    }*/
    // TODO: Update shootBullet() to shootBullet(BulletManager bulletManager)!
    // TODO: Provide a getter and setter for shootingPattern
    public ShootingPattern getShootingPattern() {
        return shootingPattern;
    }

    public void setShootingPattern(ShootingPattern shootingPattern) {
        this.shootingPattern = shootingPattern;
    }

    // Execute Spell Card (Bomb) from BombCommand
    public void useBomb(BulletManager bulletManager, List<GameObject> entities) {
        // TODO:
        // 1. Check whether spellCards > 0.
        if (spellCards > 0){
            spellCards -= 1;
            invulnerableTimer = 5.0f;
            notifySpellCardsChanged();

            if (bulletManager != null){
                bulletManager.clearEnemyBullets(entities);
                System.out.println(name + " unleashes SPEL CARD (Fantasy Seal)! All enemy bullets cleared");
                // Search for an active enemy target to chase (Boss or Fairy)
                GameObject target = null;
                if (entities != null) {
                    for (GameObject obj : entities) {
                        if (obj instanceof Enemy enemy && !enemy.isDestroyed()) {
                            target = enemy;
                            break;
                        }
                    }
                }

                // TODO (2 points): Call spawnBombOrbs on bulletManager from Player center (x + width / 2f, y + height / 2f) with target and entities arguments
                bulletManager.spawnBombOrbs(x + width / 2f, y + height / 2f,target,entities);
            }
            System.out.println(name + " unleashes SPELL CARD: Spirit Sign Fantasy Seal (霊符「夢想封印」)! All enemy bullets cleared & 8 homing orbs launched!");

        }
        else{
            System.out.println("No Spell Cards (Bombs) remaining");
        }
        // 2. If YES:
        //    - Decrement spellCards by 1 (spellCards--).
        //    - Activate invulnerability for 5 seconds: invulnerableTimer = 5.0f;
        //    - If bulletManager is not null, call bulletManager.clearEnemyBullets(entities).
        //    - Print to console: name + " unleashes SPELL CARD (Fantasy Seal)! All enemy bullets cleared!"
        // 3. If NO:
        //    - Print to console: "No Spell Cards (Bombs) remaining!"
    }

    public void useBomb(BulletManager bulletManager) {
        useBomb(bulletManager, null);
    }

    public void addGraze() {
        // TODO:
        // 1. Increment grazeCount by 1 (this.grazeCount++)
        // 2. Add 50 to the score using addScore(50)
        this.grazeCount++;
        addScore(50L);
        notifyGrazeChanged();
    }


    @Override
    public void shootBullet(BulletManager bulletManager) {
        if (bulletManager == null) return;
        if (shootTimer >= shootCooldown) {
            // TODO (2 points): On this line, check if mainShootingPattern is not null. If it is, call mainShootingPattern.execute(x + width / 2f - 8f, y + height, bulletManager, true). Then change the `if` below (which directly checks `shootingPattern != null`) to an `else if`
            if (mainShootingPattern != null){
                mainShootingPattern.execute(x + width / 2f - 8f, y + height, bulletManager, true);
            }
            else if (shootingPattern != null) {
                shootingPattern.execute(x + width / 2f - 8f, y + height, bulletManager, true);
            } else {
                bulletManager.spawnPlayerBullet(x + width / 2f - 8f, y + height, 0, 400f, 10 + power);
            }
            shootTimer = 0f;
        }

        // TODO (2 points): If sideShootingPattern is not null and homingShootTimer is greater than or equal to homingCooldown,
        // call sideShootingPattern.execute(x + width / 2f - 8f, y + height, bulletManager, true), then reset homingShootTimer to zero.
        if(sideShootingPattern != null && homingShootTimer >= homingCooldown){
        sideShootingPattern.execute(x + width / 2f - 8f, y + height, bulletManager, true);
        this.homingShootTimer = 0f;
    }
        }

    public ShootingPattern getMainShootingPattern() { return mainShootingPattern; }
    public ShootingPattern getSideShootingPattern() { return sideShootingPattern; }
    public float getShootCooldown() { return shootCooldown; }
    public float getHomingCooldown() { return homingCooldown; }


    public void addScore(long points) {
        // TODO: Add the value to the player's score if points is greater than 0.
        if (points > 0) {
            this.score += points;
            notifyScoreChanged();
            System.out.println(getName() + " gained " + points + " pts! Total Score: " + this.score);
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        // TODO: Increment shootTimer by delta (shootTimer += delta;)
        //       Make sure all old Gdx.input reading code inside update() has been removed
        shootTimer += delta;
        homingShootTimer += delta;

        if (shootTimer > 0.15f) {
            shootTimer = shootCooldown;
            homingShootTimer = homingCooldown;
        }
        if (invulnerableTimer > 0f) {
            invulnerableTimer -= delta;
            if (invulnerableTimer < 0f) invulnerableTimer = 0f;
        }

    }
    // Execute movement from MoveCommand with playfield boundary clamping (Playfield Clamping)
    public void move(float dx, float dy) {
        // TODO:
        // 1. Increment x position by dx multiplied by speed (this.x += dx * speed)
        // 2. Increment y position by dy multiplied by speed (this.y += dy * speed)
        // 3. Call clampToPlayfield()
        this.x += dx * speed;
        this.y += dy * speed;
        clampToPlayfield();
    }

    private void clampToPlayfield() {
        float minX = 32;
        float maxX = 416 - width;
        float minY = 16;
        float maxY = 560 - height;

        // TODO: Clamp x so it is not less than minX and not greater than maxX,
        //       and clamp y so it is not less than minY and not greater than maxY.
        this.x = Math.max(minX, Math.min(this.x, maxX));
        this.y = Math.max(minY, Math.min(this.y, maxY));
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
                currentDir = -1;
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
                currentDir = 1;
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
                currentDir = 0;
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

    @Override
    public Rectangle getCoreHitbox() {
        return new Rectangle(x + (width / 2f) - 4f, y + (height / 2f) - 4f, 8f, 8f);
    }

    @Override
    public Rectangle getGrazeHitbox() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (batch != null && active && !isDestroyed()) {
            // High-frequency blinking animation while invulnerable (respawn / bomb i-frames)
            if (invulnerableTimer > 0f) {
                boolean visible = ((int) (invulnerableTimer * 16)) % 2 == 0;
                if (!visible) return; // Skip drawing this frame to create a blinking effect
            }

            if (focused) {
                batch.setColor(1f, 1f, 1f, 0.5f); // 50% opacity while Focus Mode is active
            }
            super.render(batch);
            if (focused) {
                batch.setColor(1f, 1f, 1f, 1.0f); // Restore opacity to 100%
            }
        }
    }

    // Draws the Core Hitbox indicator dot while Focus Mode (Shift) is active
    public void renderFocusIndicator(ShapeRenderer shapeRenderer) {
        if (focused && shapeRenderer != null && active) {
            Rectangle core = getCoreHitbox();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.circle(core.x + core.width / 2f, core.y + core.height / 2f, 4f);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(core.x + core.width / 2f, core.y + core.height / 2f, 2f);
            shapeRenderer.end();
        }
    }

    // TODO: Create getter isFocused(), setter setFocused(boolean focused), and getter getGrazeCount()
    public boolean isFocused(){ return this.focused; }
    public void setFocused(boolean focused){
        if (this.focused != focused) {
            this.focused = focused;
            updatePowerStrategy();
        } }
    public int getGrazeCount(){ return this.grazeCount; }

    public void takeDamage(int damage) {
        takeDamage(damage, null, null);
    }

    public void takeDamage(int damage, BulletManager bulletManager, List<GameObject> entities) {
        if (isInvulnerable() || isDestroyed()) {
            if (isInvulnerable()) {
                System.out.println(name + " is INVULNERABLE! (0 damage taken)");
            }
            return;
        }
        this.hp -= damage;
        if (this.hp < 0) {
            this.hp = 0;
        }
        notifyHpChanged();
        System.out.println(name + " took " + damage + " damage! Remaining HP: " + this.hp);
        if (this.hp == 0) {
            System.out.println(name + " was defeated (Pichuun~)! ");
            loseLife(bulletManager, entities);
        }
    }

    public void loseLife(BulletManager bulletManager, List<GameObject> entities) {
        this.lives--;
        notifyLivesChanged();
        if (bulletManager != null) {
            bulletManager.clearEnemyBullets();
        }

        if (this.lives > 0) {
            if (bulletManager != null) {
                System.out.println("[Player] " + name + " lost a life! Remaining Lives: " + this.lives + ". Respawning at (200, 50)...");
            }
            respawn();
        } else {
            if (bulletManager != null) {
                System.out.println("[Player] GAME OVER! " + name + " has run out of lives!");
            }
            destroy();
        }
    }

    public void respawn() {
        this.active = true;
        this.x = 200;
        this.y = 50;
        this.hp = 100;
        this.invulnerableTimer = 3.0f;
        this.power = Math.max(10, this.power - 16); // Touhou death penalty
        this.spellCards = 3; // Reset bombs to 3 upon respawn
        notifyHpChanged();
        notifySpellCardsChanged();
        notifyPowerChanged();
    }

    public void resetGame() {
        this.lives = 3;
        this.hp = 100;
        this.power = 15;
        this.spellCards = 3;
        this.score = 0;
        this.grazeCount = 0;
        this.active = true;
        this.x = 200;
        this.y = 50;
        this.invulnerableTimer = 3.0f;
        notifyPowerChanged();
        notifySpellCardsChanged();
        notifyLivesChanged();
        notifyHpChanged();
        notifyGrazeChanged();
        notifyScoreChanged();
    }

    public void collectItem(Item item) {
        if (item.isDestroyed()) return;

        ItemType type = item.getItemTypeEnum();
        if (type != null) {
            switch (type) {
                case POWER -> {
                    this.power += type.getPowerBonus();
                    updatePowerStrategy();
                    notifyPowerChanged();
                    addScore(item.getScoreValue());
                    System.out.println(name + " collected POWER item! Power increased to " + power);
                    notifyPowerChanged();
                }
                case POINT -> {
                    addScore(item.getScoreValue());
                    System.out.println(name + " collected POINT item!");
                }
                case BOMB -> {
                    this.spellCards++;
                    addScore(item.getScoreValue());
                    System.out.println(name + " collected BOMB item! SpellCards: " + spellCards);
                    notifySpellCardsChanged();
                }
                case LIFE -> {
                    this.lives++;   // Note: This line was added!
                    this.hp = 100;
                    addScore(item.getScoreValue());
                    System.out.println(name + " collected LIFE item! Remaining Lives: " + lives);
                    notifyLivesChanged();
                }
            }
        } else {
            addScore(item.getScoreValue());
            System.out.println(name + " collected " + item.getItemType() + "!");
        }

        item.destroy();
    }

    public boolean isAlive() {
        return this.hp > 0 && this.lives > 0 && !isDestroyed();
    }

    public boolean isGameOver() {
        return this.lives <= 0;
    }

    public boolean isInvulnerable() {
        return invulnerableTimer > 0f;
    }

    public float getInvulnerableTimer() {
        return invulnerableTimer;
    }

    public int getLives() { return lives; }
    public void setLives(int lives) {

        this.lives = lives;
        notifyLivesChanged();
        notifyHpChanged();
        notifyPowerChanged();
        notifySpellCardsChanged();
    }
    // TODO (6 points): Implement Dynamic Strategy Swapping on Player Reimu
    public void updatePowerStrategy() {
        float spreadAngle = focused ? 4f : 18f;
        float dualSpreadAngle = focused ? 3f : 12f;
        float sideHOffset = focused ? 8f : 24f;
        float sideVOffset = focused ? 16f : -16f;
        float leftAngle = focused ? 100f : 120f;
        float rightAngle = focused ? 80f : 60f;

        float normalSpeed = 720f;
        float homingSpeed = 500f;

        // 2. Evaluate player power ranks (Authentic Touhou 6 EoSD Power Ranks):
        if (power < 8) {
            // Rank 1 (Power 0 - 7): 1 Center Straight Shot
            // TODO: Set Main Shooting Pattern as FrontShot with normalSpeed and DMG 48
            // TODO: Set Side Shooting Pattern as null
            mainShootingPattern = new FrontShot(normalSpeed,48);
            sideShootingPattern = null;
            homingCooldown = 30f / 60f;
        } else if (power < 16) {
            // Rank 2 (Power 8 - 15): 1 Center Straight Shot + 2 Homing Flank Amulets (Homing SideShot)
            // TODO: Set Main Shooting Pattern as FrontShot with normalSpeed and DMG 48
            // TODO: Set Side Shooting Pattern as SideShot with homingSpeed, DMG 14, offsets (sideHOffset, sideVOffset), angles (leftAngle, rightAngle), dualSides true; with bullet movement HomingBulletMovement having turnSpeed 360f; and sprite "bullet_amulet_homing"
            mainShootingPattern = new FrontShot(normalSpeed, 48);
            sideShootingPattern = new SideShot(homingSpeed,14,sideHOffset,sideVOffset,leftAngle,rightAngle,true, new HomingBulletMovement(360f),"bullet_amulet_homing");
            homingCooldown = 30f / 60f;
        } else if (power < 32) {
            // Rank 3 (Power 16 - 31): 2 Tightly Spaced Center Straight Shots + 2 Homing Flank Amulets
            // TODO: Set Main Shooting Pattern as SpreadShot with normalSpeed, 2 bullets, angle dualSpreadAngle, DMG 30, horizontalSpacing 8f, and null for movementPattern, target, and spriteKey
            // TODO: Set Side Shooting Pattern as SideShot with homingSpeed, DMG 14, offsets (sideHOffset, sideVOffset), angles (leftAngle, rightAngle), dualSides true; with bullet movement HomingBulletMovement having turnSpeed 360f; and sprite "bullet_amulet_homing"
            mainShootingPattern = new SpreadShot(normalSpeed,2,dualSpreadAngle,30, 8f,null,null, null);
            sideShootingPattern = new SideShot(homingSpeed,14,sideHOffset,sideVOffset,leftAngle,rightAngle,true, new HomingBulletMovement(360f),"bullet_amulet_homing");
            homingCooldown = 30f / 60f;
        } else if (power < 48) {
            // Rank 4 (Power 32 - 47): 3 Center Fan Spread Shots + 2 Homing Flank Amulets
            // TODO: Set Main Shooting Pattern as SpreadShot with normalSpeed, 3 bullets, angle spreadAngle, and DMG 26
            // TODO: Set Side Shooting Pattern as SideShot with homingSpeed, DMG 14, offsets (sideHOffset, sideVOffset), angles (leftAngle, rightAngle), dualSides true; with bullet movement HomingBulletMovement having turnSpeed 360f; and sprite "bullet_amulet_homing"
            mainShootingPattern = new SpreadShot(normalSpeed,3,spreadAngle,26);
            sideShootingPattern = new SideShot(homingSpeed,14,sideHOffset,sideVOffset,leftAngle,rightAngle,true, new HomingBulletMovement(360f),"bullet_amulet_homing");
            homingCooldown = 30f / 60f;
        } else if (power < 64) {
            // Rank 5 (Power 48 - 63): 3 Center Fan Spread Shots + Homing Amulets (Cadence accelerated to 15 frames / 0.25s!)
            // TODO: Set Main Shooting Pattern as SpreadShot with normalSpeed, 3 bullets, angle spreadAngle, and DMG 26
            // TODO: Set Side Shooting Pattern as SideShot with homingSpeed, DMG 12, offsets (sideHOffset, sideVOffset), angles (leftAngle, rightAngle), dualSides true; with bullet movement HomingBulletMovement having turnSpeed 360f; and sprite "bullet_amulet_homing"
            mainShootingPattern = new SpreadShot(normalSpeed,3,spreadAngle,26);
            sideShootingPattern =  new SideShot(homingSpeed,12,sideHOffset,sideVOffset,leftAngle,rightAngle,true, new HomingBulletMovement(360f),"bullet_amulet_homing");
            homingCooldown = 15f / 60f;
        } else if (power < 80) {
            // Rank 6 (Power 64 - 79): 3 Center Fan Spread Shots + 4 Homing Amulets (Composite Pattern)
            // TODO: Set Main Shooting Pattern as SpreadShot with normalSpeed, 3 bullets, angle spreadAngle, and DMG 26
            // TODO: Set Side Shooting Pattern as CompositeShootingPattern with 2 SideShots:
            //       1) SideShot with homingSpeed, DMG 9, offsets (sideHOffset, sideVOffset), angles (leftAngle, rightAngle), dualSides true; with bullet movement HomingBulletMovement having turnSpeed 360f; and sprite "bullet_amulet_homing"
            //       2) SideShot with homingSpeed, DMG 12, offsets (sideHOffset + 4f, sideVOffset), angles (leftAngle + 30f, rightAngle - 30f), dualSides true; with bullet movement HomingBulletMovement having turnSpeed 360f; and sprite "bullet_amulet_homing"
            mainShootingPattern = new SpreadShot(normalSpeed,3,spreadAngle,26);
            sideShootingPattern = new CompositeShootingPattern(new SideShot(homingSpeed,9,sideHOffset,sideVOffset,leftAngle,rightAngle,true,new HomingBulletMovement(360f),"bullet_amulet_homing"),new SideShot(homingSpeed,12,sideHOffset+4f,sideVOffset,leftAngle+30f,rightAngle-30f,true,new HomingBulletMovement(360f),"bullet_amulet_homing") );
            homingCooldown = 15f / 60f;
        } else if (power < 128) {
            // Rank 7 & 8 (Power 80 - 127): 3 Center Fan Spread Shots + 4 Tightly Angled Homing Amulets
            // TODO: Set Main Shooting Pattern as SpreadShot with normalSpeed, 3 bullets, angle spreadAngle, and DMG 26
            // TODO: Set Side Shooting Pattern as CompositeShootingPattern with 2 SideShots:
            //       1) SideShot with homingSpeed, DMG 8, offsets (sideHOffset, sideVOffset), angles (leftAngle - 10f, rightAngle + 10f), dualSides true; with bullet movement HomingBulletMovement having turnSpeed 360f; and sprite "bullet_amulet_homing"
            //       2) SideShot with homingSpeed, DMG 8, offsets (sideHOffset + 4f, sideVOffset), angles (leftAngle + 10f, rightAngle - 10f), dualSides true; with bullet movement HomingBulletMovement having turnSpeed 360f; and sprite "bullet_amulet_homing"
            mainShootingPattern = new SpreadShot(normalSpeed,3,spreadAngle,26);
            sideShootingPattern = new CompositeShootingPattern(new SideShot(homingSpeed,8,sideHOffset,sideVOffset,leftAngle-10f,rightAngle+10f,true,new HomingBulletMovement(360f),"bullet_amulet_homing"),
                new SideShot(homingSpeed,8,sideHOffset+4f,sideVOffset,leftAngle+10f,rightAngle-10f,true, new HomingBulletMovement(360f),"bullet_amulet_homing"));
            homingCooldown = 15f / 60f;
        } else {
            // Rank 9 (Power 128 MAX): 4 Spaced Center Fan Spread Shots + 6 Maximum Homing Amulets
            // TODO: Set Main Shooting Pattern as SpreadShot with normalSpeed, 4 bullets, angle spreadAngle * 1.2f, DMG 24, horizontalSpacing 16f, and null for movementPattern, target, and spriteKey
            // TODO: Set Side Shooting Pattern as CompositeShootingPattern with 3 SideShots:
            //       1) SideShot with homingSpeed, DMG 8, offsets (sideHOffset, sideVOffset), angles (leftAngle - 10f, rightAngle + 10f), dualSides true; with bullet movement HomingBulletMovement having turnSpeed 360f; and sprite "bullet_amulet_homing"
            //       2) SideShot with homingSpeed, DMG 8, offsets (sideHOffset + 4f, sideVOffset), angles (leftAngle + 10f, rightAngle - 10f), dualSides true; with bullet movement HomingBulletMovement having turnSpeed 360f; and sprite "bullet_amulet_homing"
            //       3) SideShot with homingSpeed, DMG 8, offsets (sideHOffset + 8f, sideVOffset), angles (leftAngle + 30f, rightAngle - 30f), dualSides true; with bullet movement HomingBulletMovement having turnSpeed 360f; and sprite "bullet_amulet_homing"
            mainShootingPattern = new SpreadShot(normalSpeed,4,spreadAngle*1.2f,24,16f,null,null,null);
            sideShootingPattern = new CompositeShootingPattern(new SideShot(homingSpeed,8,sideHOffset,sideVOffset,leftAngle-10f,rightAngle+10f,true,new HomingBulletMovement(360f),"bullet_amulet_homing"),
                new SideShot(homingSpeed,8,sideHOffset+4f,sideVOffset,leftAngle+10f,rightAngle-10f,true,new HomingBulletMovement(360f),"bullet_amulet_homing"),
                new SideShot(homingSpeed,8,sideHOffset+8f,sideVOffset,leftAngle+30f,rightAngle-30f,true,new HomingBulletMovement(360f),"bullet_amulet_homing"));
            homingCooldown = 15f / 60f;
        }

        // 3. Attach composite strategy to superclass EntityShooter attribute
        if (sideShootingPattern != null) {
            setShootingPattern(new CompositeShootingPattern(mainShootingPattern, sideShootingPattern));
        } else {
            setShootingPattern(mainShootingPattern);
        }
    }







}
