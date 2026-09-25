package com.said.frontend.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject implements Collidable{
    protected float x,y,width,height,speed;
    protected Color color;
    protected boolean active = true;

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
    public Color getColor(){
        return this.color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public
    void update(float delta){}
    /*Its intentionally empty because we want to then override them in other classes*/

    public void render(ShapeRenderer shapeRenderer){
        if (shapeRenderer != null && color != null && active) {
            shapeRenderer.setColor(color);
            shapeRenderer.rect(x, y, width, height);
        }
    }
    //PreCS M4
    public boolean isDestroyed() {
        // TODO: return true if the object is NOT active (active == false)
        if (!active){
            return true;
        }
        return false;
    }

    public void destroy() {
        // TODO: mark this object as inactive
        active = false;
    }

    public boolean isOffScreen(float screenWidth, float screenHeight) {
        // TODO: return true if the x or y position is outside the screen boundaries
        if (x-50 > screenWidth || x < -50 || y-50 > screenHeight || y < -50){
            return true;
        }
        return false;
        // Use a 50px tolerance margin on each side, so objects that have only
        // slightly passed the edge of the screen are not immediately considered gone.

    }

    @Override
    public Rectangle getCoreHitbox(){
        return new Rectangle(x,y,width,height);
    }

    @Override
    public Rectangle getGrazeHitbox() {
        // TODO: return a Rectangle with +10px padding on every side
        return new Rectangle(x-10,y-10,width+20,height+20);
    }
    @Override
    public void onCollision(Collidable other) {
        // Base collision handler (can be overridden by subclasses that need to react)
    }
}

