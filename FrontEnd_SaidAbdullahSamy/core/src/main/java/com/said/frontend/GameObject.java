package com.said.frontend;

import java.awt.*;

public class GameObject {
    protected float x,y,width,height,speed;
    protected Color color;

    public GameObject(float x, float y, float width, float height, float speed, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.color = color;
    }
    public float getX(){
        return this.x;
    }
    public void setX(float x){
        this.x = x;
    }
    public float getY(){
        return this.y;
    }
    public void setY(float y){
        this.y = y;
    }
    public float getWidth (){
        return this.width;
    }
    public void setWidth(float width){
        if (width > 0) this.width = width;
    }
    public float getHeight(){
        return this.height;
    }
    public void setHeight(float height){
        if (height > 0) this.height = height;
    }
    public float getSpeed(){
        return this.speed;
    }
    public void setSpeed(float speed){
        if (speed >= 0) this.speed = speed;
    }
    public float getColor(){
        return this.color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public
    void update(float delta){}
    /*Its intentionally empty because we want to then override them in other classes*/

    void render(ShapreRenderer shapeRenderer){}
}

