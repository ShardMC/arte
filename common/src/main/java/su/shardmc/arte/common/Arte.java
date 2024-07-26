package su.shardmc.arte.common;

import su.shardmc.arte.common.config.ArteConfig;
import su.shardmc.arte.common.logger.ArteLogger;
import su.shardmc.arte.common.pack.manager.PackManager;
import su.shardmc.arte.common.platform.Platform;

import java.util.ServiceLoader;
import java.util.stream.Stream;

public interface Arte {
    ArteLogger logger();
    ArteConfig config();
    PackManager getPackManager();
    Platform<?> platform();

    default <T extends Platform<?>> Stream<T> loadPlatform(ClassLoader loader, Class<T> clazz) {
        return ServiceLoader.load(clazz, loader).stream()
                .map(ServiceLoader.Provider::get);
    }
}
