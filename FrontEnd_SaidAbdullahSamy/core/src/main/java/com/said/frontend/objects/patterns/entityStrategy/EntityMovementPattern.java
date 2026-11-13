package com.said.frontend.objects.patterns.entityStrategy;

import com.said.frontend.objects.GameObject;

public interface EntityMovementPattern {
    // TODO (4 points): Declare the `move` method with the contract and parameters described above.
    default void move(GameObject entity, float delta){

    }
}

