package com.said.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.said.frontend.objects.GameObject;
import com.said.frontend.objects.Player;
import com.said.frontend.objects.enemies.Boss;
import com.said.frontend.objects.enemies.Fairy;
import com.said.frontend.objects.items.Item;
import com.said.frontend.objects.items.ItemType;

import com.badlogic.gdx.Input;
import com.said.frontend.objects.systems.AssetManager;
import com.said.frontend.objects.systems.EntityFactory;

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


    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        entities = new ArrayList<>();
        fairyList = new ArrayList<>();
        batch = new SpriteBatch();

        // TODO 1:
        // When initializing the renderer, create a SpriteBatch and store it in batch.
        // LibGDX hint: new SpriteBatch().
        // TODO 2:
        // Initialize the fairy and entities lists as empty ArrayLists.

        // TODO 3:
        // Before creating entities, get the AssetManager instance and call init().
        AssetManager.getInstance().init();
        // TODO 4:
        // Update how all entities are created! Follow the table and create Player, Fairy, Boss, and Item
        // using the appropriate EntityFactory methods.
        // Add both Fairies to the fairy list using add(...).
        playerObject = EntityFactory.createPlayer(280,40,"Reimu Hakurei",100,15,3);

        // TODO 3: Instantiate Fairy (Pink square) at (150, 380)
        // fairyObject = new Fairy(150,380,"Red Fairy",20);

        // TODO 4: Instantiate Boss (Blue square) at (380, 400)
        bossObject = EntityFactory.createBoss(380,400,"Boss1",150);
        // TODO 5: Instantiate Items (White squares) with downward speeds
        itemsObject = EntityFactory.createItem(150,450,ItemType.POWER);

        powerItem = EntityFactory.createItem(200, 450, ItemType.POWER);
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

        // TODO 1: If the Z key was just pressed, add a new bullet from player.shootBullet()
        // to the entities list.
        // Clue: Gdx.input.isKeyJustPressed()
        if(Gdx.input.isKeyJustPressed(Input.Keys.Z)){

            entities.add(playerObject.shootBullet());

        }
        updateAndClean(entities,delta,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        // TODO 2: Call updateAndClean(entities, delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight())
        // to update and clean up destroyed/off-screen entities.
        // 1. Polymorphic Update Loop: Items move downward automatically via Item.update(delta)
        for (GameObject obj : entities) {
            obj.update(delta);
        }
        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                GameObject a = entities.get(i);
                GameObject b = entities.get(j);

                // TODO: Check whether getCoreHitbox() of a and b overlap (use the .overlaps() method of Rectangle)
                if(a.getCoreHitbox().overlaps(b.getCoreHitbox())){
                    a.onCollision(b);
                    b.onCollision(a);
                }
                // TODO: Call a.onCollision(b) and b.onCollision(a)
            }
        }

        // 2. Clear Screen
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        // 3. Polymorphic Render Loop: Draw hitboxes with ShapeRenderer
        /*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (GameObject obj : entities) {
            if (!obj.isDestroyed()){
                obj.render(shapeRenderer);
            }
        }
        shapeRenderer.end();*/

        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        batch.begin();
        for (GameObject entity : entities) {
            if (!entity.isDestroyed()) {
                // TODO: Call each entity's .render() method with the SpriteBatch as its argument.
                entity.render(batch);
            }
        }
        batch.end();
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
