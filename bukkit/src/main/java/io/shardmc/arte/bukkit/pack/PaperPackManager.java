package io.shardmc.arte.bukkit.pack;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import io.shardmc.arte.bukkit.ArtePlugin;
import io.shardmc.arte.common.pack.meta.BuiltPack;
import org.bukkit.entity.Player;

public class PaperPackManager extends BukkitPackManager {

    private final ProtocolManager manager = ProtocolLibrary.getProtocolManager();

    public PaperPackManager(ArtePlugin arte) {
        super(arte);
    }

    @Override
    protected void apply(Player player, BuiltPack pack, String prompt) {
        PacketContainer packet = this.manager.createPacket(PacketType.Play.Server.ADD_RESOURCE_PACK, false);
        packet.getUUIDs().write(0, pack.uuid());
        packet.getStrings().write(0, pack.getAddress(this.server));
        packet.getStrings().write(1, pack.hash());
        packet.getBooleans().write(0, pack.force());
        packet.getChatComponents().write(0, WrappedChatComponent.fromText(prompt));

        this.manager.sendServerPacket(player, packet);
    }
}
