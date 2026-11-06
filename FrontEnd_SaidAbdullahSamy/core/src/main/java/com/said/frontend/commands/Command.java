package com.said.frontend.commands;

import com.said.frontend.objects.Player;
import com.said.frontend.objects.systems.BulletManager;

public interface Command {
    void execute(Player player, BulletManager bulletManager);
}
