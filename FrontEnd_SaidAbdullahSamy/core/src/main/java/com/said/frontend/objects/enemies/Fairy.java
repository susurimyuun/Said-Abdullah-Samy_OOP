package com.said.frontend.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.said.frontend.objects.Collidable;
import com.said.frontend.objects.Player;
import com.said.frontend.objects.enemies.Enemy;

public class Fairy extends Enemy {


    public Fairy(String name, int hp){
        super(150,380,24,24, Color.PINK,name,hp,500L);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.scoreValue = 500L;

    }
    public Fairy(float x, float y, String name, int hp){

        super(x,y,24,24,Color.PINK,name,hp,500L);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
    }

    @Override
    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is a Player
        if(other instanceof Player){
            System.out.println("Player Touches Fairy");
        }
        // TODO: Print "Player touches fairy"
    }

}
