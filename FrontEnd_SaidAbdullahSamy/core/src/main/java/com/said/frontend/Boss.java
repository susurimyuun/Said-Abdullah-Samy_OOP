package com.said.frontend;

import java.awt.*;

public class Boss extends Enemy{

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
}
/* The inheritance that is given tot he Boos/Fairy type is that they can take the methods n attributes from Enemy n GameObject*/
