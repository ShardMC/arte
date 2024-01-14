package io.shardmc.arte.bukkit.pack.base;

import io.shardmc.arte.bukkit.ArtePlugin;
import io.shardmc.arte.common.pack.manager.PackManager;
import io.shardmc.arte.common.pack.meta.BuiltPack;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public abstract class BukkitPackManager extends PackManager {

    protected final Map<Player, Set<UUID>> map = new HashMap<>();

    public BukkitPackManager(ArtePlugin arte) {
        super(arte);
    }

    public void apply(Player player) {
        this.arte.logger().info("Applying pack to player " + player.getName());
        String prompt = this.arte.config().getPrompt();

        if (this.zipper.getPacks().isEmpty())
            return;

        this.applyEffects(player);
        for (BuiltPack pack : this.zipper.getPacks()) {
            this.apply(player, pack, prompt);
        }
    }

    protected void setStatus(Player player, UUID uuid, Status status) {
        Set<UUID> set = this.map.computeIfAbsent(player, k -> new HashSet<>());

        switch (status) {
            case WORKING -> set.add(uuid);
            case DONE -> set.remove(uuid);
        }

        if (set.isEmpty()) {
            this.map.remove(player);
            this.clearEffects(player);
        }
    }

    protected abstract void apply(Player player, BuiltPack pack, String prompt);

    protected void applyEffects(Player player) {
        player.setInvulnerable(true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, PotionEffect.INFINITE_DURATION, 255, true), true);
    }

    protected void clearEffects(Player player) {
        player.setInvulnerable(false);
        player.removePotionEffect(PotionEffectType.BLINDNESS);
    }

    protected enum Status {
        WORKING,
        DONE;
    }
}
