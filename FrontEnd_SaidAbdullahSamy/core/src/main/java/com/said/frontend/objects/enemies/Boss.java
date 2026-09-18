package com.said.frontend.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.said.frontend.objects.Collidable;
import com.said.frontend.objects.Player;
import com.said.frontend.objects.enemies.Enemy;


public class Boss extends Enemy {

    public Boss(String name, int hp){
        super(380,400,48,48, Color.BLUE,name,hp,5000L);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.scoreValue = 5000L;
    }
    public Boss(float x, float y, String name, int hp){

        super(x,y,48,48,Color.BLUE,name,hp,5000L);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
    }

    @Override
    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is a Player
        if(other instanceof Player){
            System.out.println("Player Touches Boss");
        }
        // TODO: Print "Player touches fairy"
    }
}
/* The inheritance that is given tot he Boos/Fairy type is that they can take the methods n attributes from Enemy n GameObject*/
