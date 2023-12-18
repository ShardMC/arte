package the.grid.smp.arte.bukkit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import the.grid.smp.arte.bukkit.ArtePlugin;
import the.grid.smp.arte.bukkit.pack.BukkitPackManager;

public class PlayerListener implements Listener {

    private final ArtePlugin arte;

    public PlayerListener(ArtePlugin arte) {
        this.arte = arte;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (this.arte.getPackManager() instanceof BukkitPackManager bukkit)
            bukkit.apply(event.getPlayer());
    }
}
