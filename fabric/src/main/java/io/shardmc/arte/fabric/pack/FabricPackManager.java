package io.shardmc.arte.fabric.pack;

import net.minecraft.network.packet.s2c.common.ResourcePackSendS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import io.shardmc.arte.common.Arte;
import io.shardmc.arte.common.pack.manager.PackManager;
import io.shardmc.arte.common.pack.meta.BuiltPack;

import java.util.*;

public class FabricPackManager extends PackManager {

    protected final Map<ServerPlayerEntity, Set<UUID>> map = new HashMap<>();

    public FabricPackManager(Arte arte) {
        super(arte);
    }

    public void apply(ServerPlayerEntity player) {
        this.arte.logger().info("Applying pack to player " + player.getName().getString());
        Text prompt = Text.of(this.arte.config().getPrompt());

        this.applyEffects(player);
        for (BuiltPack pack : this.zipper.getPacks()) {
            player.networkHandler.sendPacket(new ResourcePackSendS2CPacket(pack.uuid(), pack.getAddress(this.server), pack.hash(), pack.force(), prompt));
        }
    }

    protected void setStatus(ServerPlayerEntity player, UUID uuid, Status status) {
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

    protected void applyEffects(ServerPlayerEntity player) {
        player.setInvulnerable(true);
    }

    protected void clearEffects(ServerPlayerEntity player) {
        player.setInvulnerable(false);
    }

    protected enum Status {
        WORKING,
        DONE
    }
}
