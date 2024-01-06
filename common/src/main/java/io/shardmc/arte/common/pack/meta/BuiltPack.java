package io.shardmc.arte.common.pack.meta;

import io.shardmc.arte.common.util.Util;
import io.shardmc.arte.common.web.Hostable;
import io.shardmc.arte.common.web.WebServer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public record BuiltPack(Path path, String hash, UUID uuid, boolean force) implements Hostable {

    public BuiltPack(Path path, boolean force) throws IOException {
        this(path, Util.hash(path), Util.uuid(path), force);
    }

    @Override
    public void host(WebServer server) throws IOException {
        server.host(this, this.path);
    }

    @Override
    public String getName() {
        return this.uuid.toString();
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
