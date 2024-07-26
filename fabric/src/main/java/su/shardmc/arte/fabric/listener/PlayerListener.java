package io.shardmc.arte.fabric.listener;

import io.shardmc.arte.fabric.ArteMod;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;

public class PlayerListener implements ServerPlayConnectionEvents.Join {

    private final ArteMod arte;

    public PlayerListener(ArteMod arte) {
        this.arte = arte;
    }

    @Override
    public void onPlayReady(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        this.arte.getPackManager().apply(handler.getPlayer());
    }
}
