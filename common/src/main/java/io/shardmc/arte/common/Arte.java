package io.shardmc.arte.common;

import io.shardmc.arte.common.config.ArteConfig;
import io.shardmc.arte.common.logger.ArteLogger;
import io.shardmc.arte.common.pack.manager.PackManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface Arte {
    ArteLogger logger();
    ArteConfig config();
    PackManager getPackManager();

    File getDataFolder();
    File getConfigFile();

    URL getResourceUrl(String path) throws IOException;

    default InputStream getResourceStream(String path) throws IOException {
        return this.getResourceUrl(path).openStream();
    }

    default File getResourceFile(String path) throws IOException {
        return new File(this.getResourceUrl(path).getFile());
    }
}
