package the.grid.smp.arte.fabric.pack;

import net.minecraft.network.packet.s2c.common.ResourcePackSendS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import the.grid.smp.arte.common.Arte;
import the.grid.smp.arte.common.pack.manager.impl.AsyncPackManager;
import the.grid.smp.arte.common.pack.meta.BuiltPack;

public class FabricPackManager extends AsyncPackManager {
    public FabricPackManager(Arte arte) {
        super(arte);
    }

    public void apply(ServerPlayerEntity player) {
        this.arte.getLogger().info("Applying pack to player " + player.getName());
        Text prompt = Text.of(this.arte.config().getPrompt());

        for (BuiltPack pack : this.zipper.getPacks()) {
            ResourcePackSendS2CPacket packet = new ResourcePackSendS2CPacket(pack.uuid(), pack.getAddress(this.server), pack.getName(), pack.force(), prompt);
            this.arte.getLogger().info(packet.url());
            player.networkHandler.sendPacket(packet);
        }
    }
}
