package the.grid.smp.arte.common.pack.meta;

import org.apache.commons.codec.binary.Hex;
import the.grid.smp.arte.common.util.Util;
import the.grid.smp.arte.common.web.Hostable;
import the.grid.smp.arte.common.web.WebServer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public record BuiltPack(Path path, byte[] hash, UUID uuid, boolean force) implements Hostable {

    public BuiltPack(Path path, boolean force) throws IOException {
        this(path, Util.hash(path), Util.uuid(path), force);
    }

    @Override
    public void host(WebServer server) throws IOException {
        server.host(this.path, this.getName());
    }

    @Override
    public String getName() {
        return Hex.encodeHexString(this.hash);
    }

    @Override
    public String toString() {
        return "BuiltPack{" +
                "path=" + path +
                ", hash_str=" + this.getName() +
                ", uuid=" + uuid +
                ", force=" + force +
                '}';
    }
}
