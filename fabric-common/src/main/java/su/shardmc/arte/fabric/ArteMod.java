package su.shardmc.arte.fabric;

import su.shardmc.arte.fabric.command.ArteCommand;
import su.shardmc.arte.fabric.pack.FabricPackManager;
import su.shardmc.arte.fabric.platform.FabricPlatform;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import su.shardmc.arte.common.Arte;
import su.shardmc.arte.common.config.ArteConfig;
import su.shardmc.arte.common.logger.ArteLogger;
import su.shardmc.arte.fabric.config.FabricArteConfig;
import su.shardmc.arte.fabric.listener.PlayerListener;
import su.shardmc.arte.fabric.logger.FabricArteLogger;

public class ArteMod implements DedicatedServerModInitializer, Arte {

    protected FabricPlatform platform;

    private ArteLogger logger;
    private ArteConfig config;

    private FabricPackManager packManager;

    public void onInitializeServer() {
        this.loadPlatform(this.getClass().getClassLoader(), FabricPlatform.class)
                .findFirst().ifPresent(platform -> this.platform = platform);

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
    public FabricPlatform platform() {
        return platform;
    }
}
