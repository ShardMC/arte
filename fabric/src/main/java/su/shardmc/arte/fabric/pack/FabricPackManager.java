package io.shardmc.arte.fabric.pack;

import io.shardmc.arte.fabric.ArteMod;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import io.shardmc.arte.common.Arte;
import io.shardmc.arte.common.pack.manager.PackManager;
import io.shardmc.arte.common.pack.meta.BuiltPack;

public class FabricPackManager extends PackManager {

    public FabricPackManager(Arte arte) {
        super(arte);
    }

    public void apply(ServerPlayerEntity player) {
        this.arte.logger().info("Applying pack to player " + player.getName().getString());
        Text prompt = Text.of(this.arte.config().getPrompt());

        for (BuiltPack pack : this.zipper.getPacks()) {
            this.arte.platform().applyPack(player, pack.uuid(), pack.getAddress(this.server), pack.hash(), pack.force(), prompt);
        }
    }
}
