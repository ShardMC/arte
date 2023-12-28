package io.shardmc.arte.fabric;

import io.shardmc.arte.fabric.command.ArteCommand;
import io.shardmc.arte.fabric.pack.FabricPackManager;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import io.shardmc.arte.common.Arte;
import io.shardmc.arte.common.config.ArteConfig;
import io.shardmc.arte.common.logger.ArteLogger;
import io.shardmc.arte.fabric.config.FabricArteConfig;
import io.shardmc.arte.fabric.listener.PlayerListener;
import io.shardmc.arte.fabric.logger.FabricArteLogger;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ArteMod implements DedicatedServerModInitializer, Arte {

    private ArteLogger logger;
    private ArteConfig config;
    private FabricPackManager packManager;

    public void onInitializeServer() {
        this.logger = new FabricArteLogger("arte");
        this.config = new FabricArteConfig(this);

        this.packManager = new FabricPackManager(this);

        CommandRegistrationCallback.EVENT.register(new ArteCommand(this));
        ServerPlayConnectionEvents.JOIN.register(new PlayerListener(this));

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> this.config.save());
    }

    @Override
    public ArteLogger logger() {
        return this.logger;
    }

    @Override
    public ArteConfig config() {
        return this.config;
    }

    @Override
    public FabricPackManager getPackManager() {
        return this.packManager;
    }

    @Override
    public File getDataFolder() {
        return FabricLoader.getInstance().getGameDir().resolve("arte").toFile();
    }

    @Override
    public File getConfigFile() {
        return FabricLoader.getInstance().getConfigDir().resolve("arte.json").toFile();
    }

    @Override
    public URL getResourceUrl(String path) throws IOException {
        URL url = this.getClass().getClassLoader().getResource(path);

        if (url == null)
            throw new IOException("Couldn't find the default config! The build may be corrupt! Path: " + path);

        return url;
    }
}
