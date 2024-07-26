package su.shardmc.arte.common.platform;

import su.shardmc.arte.common.Arte;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

public interface Platform<T extends Arte> {

    default void init(T arte) { }

    File getDataFolder();

    File getConfigFile();

    default URL getResourceUrl(String path) throws IOException {
        URL url = this.getClass().getClassLoader().getResource(path);

        if (url == null)
            throw new IOException("Couldn't find the default config! The build may be corrupt! Path: " + path);

        return url;
    }

    default InputStream getResourceStream(String path) throws IOException {
        return this.getResourceUrl(path).openStream();
    }

    default File getResourceFile(String path) throws IOException {
        return new File(this.getResourceUrl(path).getFile());
    }

    void applyPack(Object player, UUID id, String address, String hash, boolean force, Object text);
}
