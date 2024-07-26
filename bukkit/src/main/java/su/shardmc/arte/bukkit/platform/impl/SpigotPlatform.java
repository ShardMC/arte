package su.shardmc.arte.bukkit.platform.impl;

import su.shardmc.arte.bukkit.platform.BukkitPlatform;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SpigotPlatform extends BukkitPlatform {

    @Override
    public void applyPack(Player player, UUID id, String address, String hash, boolean force, String text) {
        player.setResourcePack(id, address, hash.getBytes(), text, force);
    }
}
