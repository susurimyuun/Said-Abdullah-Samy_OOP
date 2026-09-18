package com.said.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.said.frontend.objects.GameObject;
import com.said.frontend.objects.Player;
import com.said.frontend.objects.enemies.Boss;
import com.said.frontend.objects.enemies.Fairy;
import com.said.frontend.objects.items.Item;
import com.said.frontend.objects.items.ItemType;

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


    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        entities = new ArrayList<>();

        // TODO 2: Instantiate Player (Red square) at (280, 40)
        playerObject = new Player(280,40,"Reimu Hakurei",100,15,3);

        // TODO 3: Instantiate Fairy (Pink square) at (150, 380)
        fairyObject = new Fairy(150,380,"Fairy1",20);

        // TODO 4: Instantiate Boss (Blue square) at (380, 400)
        bossObject = new Boss(380,400,"Boss1",150);

        // TODO 5: Instantiate Items (White squares) with downward speeds
        itemsObject = new Item(150,450,"Item1");

        powerItem = new Item(200, 450, 16, 16, 80f, ItemType.POWER, 500L);
        pointItem = new Item(320, 480, 12, 12, 120f, ItemType.POINT, 1000L);

        // TODO 6: Add all entities into the gameObjects list polymorphically
        entities.add(playerObject);
        entities.add(fairyObject);
        entities.add(bossObject);
        entities.add(itemsObject);
        entities.add(powerItem);
        entities.add(pointItem);

    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

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
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (GameObject obj : entities) {
            obj.render(shapeRenderer);
        }
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
}
