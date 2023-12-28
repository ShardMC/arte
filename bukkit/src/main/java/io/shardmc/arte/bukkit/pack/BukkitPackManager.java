package io.shardmc.arte.bukkit.pack;

import org.bukkit.entity.Player;
import io.shardmc.arte.bukkit.ArtePlugin;
import io.shardmc.arte.common.pack.manager.PackManager;
import io.shardmc.arte.common.pack.meta.BuiltPack;

public class BukkitPackManager extends PackManager {

    public BukkitPackManager(ArtePlugin arte) {
        super(arte);
    }

    public void apply(Player player) {
        this.arte.logger().info("Applying pack to player " + player.getName());
        String prompt = this.arte.config().getPrompt();

        for (BuiltPack pack : this.zipper.getPacks()) {
            player.setResourcePack(pack.uuid(), pack.getAddress(this.server), pack.hash(), prompt, pack.force());
        }
    }
}
