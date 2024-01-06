package io.shardmc.arte.bukkit.pack;

import io.shardmc.arte.bukkit.ArtePlugin;
import io.shardmc.arte.common.pack.meta.BuiltPack;
import org.bukkit.entity.Player;

public class SpigotPackManager extends BukkitPackManager {

    public SpigotPackManager(ArtePlugin arte) {
        super(arte);
    }

    @Override
    protected void apply(Player player, BuiltPack pack, String prompt) {
        player.setResourcePack(pack.uuid(), pack.getAddress(this.server), pack.hash().getBytes(), prompt, pack.force());
    }
}
