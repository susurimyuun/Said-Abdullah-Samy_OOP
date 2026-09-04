package com.said.frontend;

public class Enemy {
    String name;
    int hp;
    int maxHp;

    public Enemy(String name, int hp){
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
    }
    public static void main(String[] args) {
        Enemy enemy1 = new Enemy("Reimu Hakurei",50);

        enemy1.takeDamage(10);
    }

    public void takeDamage(int damage){
        hp -= damage;
        if (hp < 0){
            hp = 0;
        }
        else {
            System.out.println(name + " took " + damage + " damage! Remaining HP: " + hp + "/" + maxHp);
        }

        if (hp == 0){
            System.out.println(name + " has been defeated!");
        }
    }
    public void attack(Player player, int damage){
        System.out.println(name + " unleashes bullet barrage on " + player.name + "!");
        player.takeDamage(damage);
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
