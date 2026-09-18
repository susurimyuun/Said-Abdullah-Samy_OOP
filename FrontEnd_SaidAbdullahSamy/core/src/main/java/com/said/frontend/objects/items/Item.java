package com.said.frontend.objects.items;

import com.badlogic.gdx.graphics.Color;
import com.said.frontend.objects.Collidable;
import com.said.frontend.objects.GameObject;
import com.said.frontend.objects.Player;


public class Item extends GameObject {
    private String itemType;
    private long scoreValue;
    private ItemType itemTypeEnum;

    public Item(float x,float y,String itemType){
        super(x,y,16,16,100f, Color.WHITE);
        this.scoreValue = 1000L;
        this.itemType = itemType;
    }
    public Item(float x, float y, float width, float height, float speed, String itemType){
        super(x,y,width,height,speed, Color.WHITE);
        this.scoreValue = 1000L;
        this.itemType = itemType;
    }
    public Item(float x, float y, float width, float height, float speed, String itemType, long scoreValue){
        super(x,y,width,height,speed,Color.WHITE);
        this.scoreValue = scoreValue;
        this.itemType = itemType;
    }
    //Module 3
    public Item(float x, float y, ItemType itemTypeEnum){
        super(x,y,16,16,100f,Color.WHITE);
        this.scoreValue = itemTypeEnum.getScoreValue();
        this.itemTypeEnum = itemTypeEnum;
        this.itemType = itemTypeEnum.name();
    }
    //Module 3
    public Item(float x, float y, float width, float height, float speed, ItemType itemTypeEnum, long scoreValue){
        super(x,y,width,height,speed,Color.WHITE);
        this.scoreValue = scoreValue;
        this.itemTypeEnum = itemTypeEnum;
        this.itemType = itemTypeEnum.name();
    }
    //Module 3
    public ItemType getItemTypeEnum() {
        return itemTypeEnum;
    }

    @Override public void update(float delta){
        this.y -= speed * delta;
    }
    /* Because we want to use the update function with different purposes*/

    public long getScoreValue(){
        return this.scoreValue;
    }
    public String getItemType(){
        return this.itemType;
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Player) {
            // Item pickup is handled on the Player side via collectItem()

        }
    }

}
