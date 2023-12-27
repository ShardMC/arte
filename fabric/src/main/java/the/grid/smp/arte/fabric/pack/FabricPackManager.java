package the.grid.smp.arte.fabric.pack;

import net.minecraft.network.packet.s2c.common.ResourcePackSendS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import the.grid.smp.arte.common.Arte;
import the.grid.smp.arte.common.pack.manager.PackManager;
import the.grid.smp.arte.common.pack.meta.BuiltPack;

public class FabricPackManager extends PackManager {

    public FabricPackManager(Arte arte) {
        super(arte);
    }

    public void apply(ServerPlayerEntity player) {
        this.arte.logger().info("Applying pack to player " + player.getName());
        Text prompt = Text.of(this.arte.config().getPrompt());

        for (BuiltPack pack : this.zipper.getPacks()) {
            player.networkHandler.sendPacket(new ResourcePackSendS2CPacket(pack.uuid(), pack.getAddress(this.server), pack.getName(), pack.force(), prompt));
        }
    }
}
