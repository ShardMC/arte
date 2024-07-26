package io.shardmc.arte.bukkit.pack;

import io.shardmc.arte.bukkit.ArtePlugin;
import io.shardmc.arte.common.pack.manager.PackManager;
import io.shardmc.arte.common.pack.meta.BuiltPack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class BukkitPackManager extends PackManager implements Listener {

    public BukkitPackManager(ArtePlugin arte) {
        super(arte);

        arte.getServer().getPluginManager().registerEvents(this, arte);
    }

    public void apply(Player player) {
        this.arte.logger().info("Applying pack to player " + player.getName());
        String prompt = this.arte.config().getPrompt();

        for (BuiltPack pack : this.zipper.getPacks()) {
            this.arte.platform().applyPack(player,
                    pack.uuid(), pack.getAddress(this.server),
                    pack.hash(), pack.force(), prompt
            );
        }
    }

    @EventHandler
    public void onPack(PlayerResourcePackStatusEvent event) {
        this.arte.logger().info("Status: " + event.getStatus());
    }
}
