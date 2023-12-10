package the.grid.smp.arte.manager;

import org.apache.commons.codec.binary.Hex;
import org.bukkit.entity.Player;
import the.grid.smp.arte.web.Hostable;
import the.grid.smp.arte.web.WebServer;
import the.grid.smp.arte.util.Util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public record ResourcePack(Path path, byte[] hash, UUID uuid, boolean force) implements Hostable {

    public ResourcePack(Path path, boolean force) throws IOException {
        this(path, Util.hash(path), Util.uuid(path), force);
    }

    public void apply(Player player, String address, String prompt) {
        player.setResourcePack(this.uuid, address, this.hash, prompt, this.force);
    }

    public void apply(Player player, WebServer server, String prompt) {
        this.apply(player, server.getAddress(this), prompt);
    }

    @Override
    public void host(WebServer server) throws IOException {
        server.host(this.path, this.getAddress());
    }

    @Override
    public String getAddress() {
        return Hex.encodeHexString(this.hash);
    }
}
