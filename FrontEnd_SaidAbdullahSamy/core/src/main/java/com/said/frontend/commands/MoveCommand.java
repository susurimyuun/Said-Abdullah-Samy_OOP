package com.said.frontend.commands;

import com.said.frontend.objects.Player;
import com.said.frontend.objects.systems.BulletManager;

public class MoveCommand implements Command {
    private float dx;
    private float dy;

    public MoveCommand(float dx, float dy) {
        // TODO: Initialize dx and dy attributes from constructor parameters
        this.dx = dx;
        this.dy = dy;
    }

    public void set(float dx, float dy) {
        // TODO: Set dx & dy according to parameters
        this.dx = dx;
        this.dy = dy;
    }

    // TODO: Override `execute` with the following business logic:
    // - Call the player's move(dx, dy) method
    // - Update the player's animation state
    @Override
    public void execute(Player player, BulletManager bulletManager){
        player.move(dx, dy);
        if (dx < 0) {
            player.updateAnimationState(dx); // or player.turnLeft() depending on your Player enum/method
        } else if (dx > 0) {
            player.updateAnimationState(dx); // or player.turnRight()
        } else {
            player.updateAnimationState(dx);
        }
    }
}
