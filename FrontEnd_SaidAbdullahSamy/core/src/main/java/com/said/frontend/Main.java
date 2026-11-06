package com.said.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.said.frontend.commands.Command;
import com.said.frontend.commands.InputHandler;
import com.said.frontend.objects.GameObject;
import com.said.frontend.objects.Player;
import com.said.frontend.objects.enemies.Boss;
import com.said.frontend.objects.enemies.Fairy;
import com.said.frontend.objects.items.Item;
import com.said.frontend.objects.items.ItemType;

import com.badlogic.gdx.Input;
import com.said.frontend.objects.systems.AssetManager;
import com.said.frontend.objects.systems.BulletManager;
import com.said.frontend.objects.systems.CollisionReferee;
import com.said.frontend.objects.systems.EntityFactory;
import com.said.frontend.ui.GameHUD;

import javax.lang.model.element.ElementVisitor;
import java.util.Iterator;

import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;

    // TODO 1: Declare fields for Player, Fairy, Boss, Items, and List<GameObject>
    private Player playerObject;
    private Fairy fairyObject;
    private Boss bossObject;
    private Item itemsObject;
    private Item powerItem;
    private Item pointItem;
    private List<GameObject> entities;
    private List<Fairy> fairyList;
    private SpriteBatch batch;
    // TODO: Create a private field to store BulletManager
    // TODO: Create a private field to store CollisionReferee
    private float enemyShootTimer = 0f;
    private BulletManager bulletManager;
    private CollisionReferee collisionReferee;
    // ...
    // TODO: Declare private fields InputHandler inputHandler and GameHUD gameHUD
    private InputHandler inputHandler;
    private GameHUD gameHUD;

    private void updateEnemyScheduler(float delta) {
        enemyShootTimer += delta;
        if (enemyShootTimer >= 1.5f) {
            bossObject.shootBullet(bulletManager);
            enemyShootTimer = 0f;
        }
    }
    private void restartGame() {
        System.out.println("[Main] Restarting game...");
        entities.clear();
        bulletManager.clearEnemyBullets();
        bulletManager.clearPlayerBullets();
        entities.add(playerObject);
        playerObject.resetGame();

        fairyList.clear();
        fairyList.add(EntityFactory.createFairy(150, 380, "Red Fairy", 20));
        fairyList.add(EntityFactory.createFairy(250, 380, "Blue Fairy", 20, "fairy_idle_blue"));
        bossObject = EntityFactory.createBoss(250, 400, "Rumia", 150);
        powerItem = EntityFactory.createItem(180, 450, ItemType.POWER);
        pointItem = EntityFactory.createItem(220, 480, ItemType.POINT);
        // TODO: Register gameHUD as an Observer on the player object!
        // Hint: player.registerObserver(gameHUD);
        playerObject.registerObserver(gameHUD);

        entities.addAll(fairyList);
        entities.add(bossObject);
        entities.add(powerItem);
        entities.add(pointItem);
        // TODO: Register the entities list to inputHandler for bullet conversion during a Bomb!
        // Hint: inputHandler.setEntities(entities);
        inputHandler.setEntities(entities);
    }


// ...



    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        entities = new ArrayList<>();
        fairyList = new ArrayList<>();
        batch = new SpriteBatch();
        bulletManager = new BulletManager();
        collisionReferee = new CollisionReferee();
        inputHandler = new InputHandler();
        gameHUD = new GameHUD();
        // TODO 1:
        // When initializing the renderer, create a SpriteBatch and store it in batch.
        // LibGDX hint: new SpriteBatch().
        // TODO 2:
        // Initialize the fairy and entities lists as empty ArrayLists.

        // TODO 3:
        // Before creating entities, get the AssetManager instance and call init().
        AssetManager.getInstance().init();
        //Playin MUSIICCCCCCC
        AssetManager.getInstance().playBGM("bgm_lalaland");
        // TODO 4:
        // Update how all entities are created! Follow the table and create Player, Fairy, Boss, and Item
        // using the appropriate EntityFactory methods.
        // Add both Fairies to the fairy list using add(...).
        playerObject = EntityFactory.createPlayer(200,50,"Reimu Hakurei",100,15,3);

        // TODO 3: Instantiate Fairy (Pink square) at (150, 380)
        // fairyObject = new Fairy(150,380,"Red Fairy",20);

        // TODO 4: Instantiate Boss (Blue square) at (380, 400)
        bossObject = EntityFactory.createBoss(250,400,"Rumia",150);
        // TODO 5: Instantiate Items (White squares) with downward speeds
        itemsObject = EntityFactory.createItem(180,450,ItemType.POWER);

        powerItem = EntityFactory.createItem(220, 450, ItemType.POWER);
        pointItem = EntityFactory.createItem(320, 480,  ItemType.POINT);

        fairyList.add(EntityFactory.createFairy(150,380,"Read Fairy",20));
        fairyList.add(EntityFactory.createFairy(250,380,"Blue Fairy",20,"fairy_idle_blue"));


        // TODO 5:
        // Add all the objects you have just created to entities.
        entities.add(playerObject);
        entities.addAll(fairyList);
        entities.add(bossObject);
        entities.add(itemsObject);
        entities.add(powerItem);
        entities.add(pointItem);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        // 0. Restart Game on 'R' key when Game Over
        // TODO: Check if Gdx.input != null AND the R key was just pressed (isKeyJustPressed(Input.Keys.R))
        //       AND player.isGameOver() is true: call restartGame();
        if(Gdx.input != null && Gdx.input.isKeyJustPressed(Input.Keys.R) && playerObject.isGameOver()){
            restartGame();
        }

        // 1. Process all player inputs through the Command Pattern (Invoker Command Stream)
        // TODO:
        // - Retrieve the command stream: List<Command> commands = inputHandler.handleInput(delta);
        // - Loop through each command in commands and call: command.execute(player, bulletManager);
        // (Remove any old manual Gdx.input checks in render() if still present!)
        List<Command> commands = inputHandler.handleInput(delta);
        for(Command comms : commands){
            comms.execute(playerObject,bulletManager);
        }

        // TODO: Periodic firing scheduler for Boss Rumia
        updateEnemyScheduler(delta);

        // TODO: Generic update & clean for standard entities (Player, Enemy, Item)
        updateAndClean(entities, delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // TODO: Update and recycle Object Pool
        bulletManager.update(delta,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        // TODO: Evaluate & resolve all collisions via
        // Mediator Pattern
        collisionReferee.resolveCollisions(playerObject,entities,bulletManager);

        // TODO: Clear screen
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        batch.begin();
        for (GameObject entity : entities) {
            if (!entity.isDestroyed()) {
                // TODO: Call each entity's .render() method with the SpriteBatch as its argument.
                entity.render(batch);
            }
        }
        // TODO: Render all active bullets from the pool
        bulletManager.render(batch);
        // TODO: Call gameHUD's render method with batch as parameter
        gameHUD.render(batch);
        batch.end();
        // 8. Render UI border frame lines & Focus Mode Core Hitbox indicator (Drawn ON TOP of sprites!)
        // TODO: Call gameHUD.renderBackground()
        // TODO: Call player.renderFocusIndicator(gameHUD.getShapeRenderer())
        gameHUD.renderBackground();
        playerObject.renderFocusIndicator(gameHUD.getShapeRenderer());
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }
        AssetManager.getInstance().dispose();
    }

    public <T extends GameObject> void updateAndClean(List<T> list, float delta, float screenWidth, float screenHeight) {
        // 1. Get an Iterator<T> from the given list.
        Iterator<T> lists = list.iterator();
        while (lists.hasNext()){
            T object = lists.next();
            object.update(delta);
            if (object.isOffScreen(screenWidth,screenHeight) || object.isDestroyed()){
                System.out.println("Removed via Generic Iterator " + object.getClass().getSimpleName());
                lists.remove();
            }
        }
        // 2. While there are still elements available (hasNext()):
        //    a. Get the current element using next() and store it in a variable of type T.
        //    b. Call update(delta) on the element.
        //    c. If the element is off-screen (isOffScreen(screenWidth, screenHeight))
        //       OR isDestroyed():
        //       - Display the message: "Removed via Generic Iterator: " + [entity class name, using getClass().getSimpleName()]
        //       - Remove the element from the list using the Iterator's method
        //         (NOT list.remove()!).
    }



}
