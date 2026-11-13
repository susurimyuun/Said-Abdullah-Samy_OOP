package com.said.frontend.objects.patterns.bulletStrategy;


import com.said.frontend.objects.bullets.Bullet;

public interface BulletMovementPattern {
    // TODO (4 points): Declare the `move` method with the contract and parameters described above.
    default void move(Bullet bullet, float delta){
        
    }
}
