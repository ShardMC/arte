package the.grid.smp.arte.bukkit.pack;

import org.bukkit.entity.Player;
import the.grid.smp.arte.common.Arte;
import the.grid.smp.arte.common.pack.manager.impl.AsyncPackManager;
import the.grid.smp.arte.common.pack.meta.BuiltPack;

public class BukkitPackManager extends AsyncPackManager {

    public BukkitPackManager(Arte arte) {
        super(arte);
    }

    public void apply(Player player) {
        this.arte.getLogger().info("Applying pack to player " + player.getName());
        String prompt = this.arte.config().getPrompt();

        for (BuiltPack pack : this.zipper.getPacks()) {
            player.setResourcePack(pack.uuid(), pack.getAddress(this.server), pack.hash(), prompt, pack.force());
        }
    }
}
