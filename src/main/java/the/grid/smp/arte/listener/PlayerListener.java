package the.grid.smp.arte.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import the.grid.smp.arte.Arte;

public class PlayerListener implements Listener {

    private final Arte arte;

    public PlayerListener(Arte arte) {
        this.arte = arte;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.arte.getPackManager().apply(event.getPlayer());
    }
}
