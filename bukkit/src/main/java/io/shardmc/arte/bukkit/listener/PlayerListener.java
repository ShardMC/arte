package io.shardmc.arte.bukkit.listener;

import io.shardmc.arte.bukkit.ArtePlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    private final ArtePlugin arte;

    public PlayerListener(ArtePlugin arte) {
        this.arte = arte;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.arte.getPackManager().apply(event.getPlayer());
    }
}
