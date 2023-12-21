package the.grid.smp.arte.fabric;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import the.grid.smp.arte.common.Arte;
import the.grid.smp.arte.fabric.command.ArteCommand;
import the.grid.smp.arte.fabric.config.FabricArteConfig;
import the.grid.smp.arte.fabric.listener.PlayerListener;
import the.grid.smp.arte.fabric.pack.FabricPackManager;

import java.io.File;
import java.util.logging.Logger;

public class ArteMod implements DedicatedServerModInitializer, Arte {
    private final Logger logger = Logger.getLogger("Arte");
    private FabricArteConfig config;
    private FabricPackManager packManager;

    public void onInitializeServer() {
        this.config = new FabricArteConfig(this);
        this.config.reload();
        CommandRegistrationCallback.EVENT.register(new ArteCommand(this));
        this.packManager = new FabricPackManager(this);
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> this.packManager.stop());
        ServerPlayConnectionEvents.JOIN.register(new PlayerListener(this));
    }

    public Logger getLogger() {
        return this.logger;
    }

    public FabricArteConfig config() {
        return this.config;
    }

    public FabricPackManager getPackManager() {
        return this.packManager;
    }

    public File getDataFolder() {
        return FabricLoader.getInstance().getGameDir().resolve("arte").toFile();
    }

    public File getConfigFile() {
        return FabricLoader.getInstance().getConfigDir().resolve("arte.json").toFile();
    }
}
