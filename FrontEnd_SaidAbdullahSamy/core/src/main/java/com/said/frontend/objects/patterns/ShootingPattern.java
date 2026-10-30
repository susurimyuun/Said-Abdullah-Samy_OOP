package com.said.frontend.objects.patterns;

import com.said.frontend.objects.systems.BulletManager;

public interface ShootingPattern {
    void execute(float originX, float originY, BulletManager bulletManager, boolean isPlayer);
}
