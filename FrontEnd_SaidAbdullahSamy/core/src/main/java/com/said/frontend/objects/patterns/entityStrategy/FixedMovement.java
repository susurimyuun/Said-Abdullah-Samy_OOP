package com.said.frontend.objects.patterns.entityStrategy;

import com.said.frontend.objects.GameObject;

public class FixedMovement implements EntityMovementPattern {
    @Override
    public void move(GameObject entity, float delta) {
        // Fixed position: no positional displacement applied to entity
    }
}
