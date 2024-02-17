package io.shardmc.arte.bukkit.pack.base;

import io.shardmc.arte.bukkit.ArtePlugin;
import io.shardmc.arte.common.pack.manager.PackManager;
import io.shardmc.arte.common.pack.meta.BuiltPack;
import org.bukkit.entity.Player;

public abstract class BukkitPackManager extends PackManager {

    public BukkitPackManager(ArtePlugin arte) {
        super(arte);
    }

    public void apply(Player player) {
        this.arte.logger().info("Applying pack to player " + player.getName());
        String prompt = this.arte.config().getPrompt();

        for (BuiltPack pack : this.zipper.getPacks()) {
            this.apply(player, pack, prompt);
        }
    }

    protected abstract void apply(Player player, BuiltPack pack, String prompt);
}
