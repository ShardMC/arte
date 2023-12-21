// Decompiled with: CFR 0.152
// Class Version: 17
package the.grid.smp.arte.fabric.listener;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import the.grid.smp.arte.fabric.ArteMod;

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
