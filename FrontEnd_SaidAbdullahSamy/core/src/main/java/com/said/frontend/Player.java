package com.said.frontend;

public class Player {
    String name;
    int hp;
    int power;
    int spellCards;

    public Player(String name, int hp, int power, int spellCards){
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;

    }
    public static void main(String[] args) {
        Player player1 = new Player("Reimu Hakurei",100,15,3);
        player1.takeDamage(10);
    }

    public void takeDamage(int damage){
        hp -= damage;
        if (hp < 0){
            hp = 0;
        }
        else {
            System.out.println(name + " took " + damage + " damage! Remaining HP: " + hp);
        }

        if (hp == 0){
            System.out.println(name + " has been defeated!");
        }
    }
    public void shoot (Enemy target){
        int damage = 10*power;
        System.out.println(name + " shoots " + target.name + " dealing " + damage + " DMG!");
        target.takeDamage(damage);
    }
    public boolean isAlive(){
        if (hp > 0){
            return true;
        }
        else{
            return false;
        }
    }

}
