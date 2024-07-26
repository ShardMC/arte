package su.shardmc.arte.bukkit.platform.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import su.shardmc.arte.bukkit.platform.BukkitPlatform;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PaperPlatform extends BukkitPlatform {

    private final ProtocolManager manager = ProtocolLibrary.getProtocolManager();

    @Override
    public void applyPack(Player player, UUID id, String address, String hash, boolean force, String text) {
        PacketContainer packet = this.manager.createPacket(PacketType.Play.Server.ADD_RESOURCE_PACK, false);
        packet.getUUIDs().write(0, id);
        packet.getStrings().write(0, address);
        packet.getStrings().write(1, hash);
        packet.getBooleans().write(0, force);
        packet.getChatComponents().write(0, WrappedChatComponent.fromText(text));

        this.manager.sendServerPacket(player, packet);
    }
}
