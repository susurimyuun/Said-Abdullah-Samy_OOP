package com.said.frontend.commands;


import com.said.frontend.objects.Player;
import com.said.frontend.objects.systems.BulletManager;

public class ShootCommand implements Command {
    // TODO: Override `execute` with the following business logic:
    // - Check if player exists (is not null)
    // - If so, call the player's shootBullet(bulletManager) method
    @Override
    public void execute(Player player, BulletManager bulletManager){
        if(player != null){
            player.shootBullet(bulletManager);
        }
    }
}

