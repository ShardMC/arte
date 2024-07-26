package su.shardmc.arte.fabric.platform;

import su.shardmc.arte.common.platform.Platform;
import su.shardmc.arte.fabric.ArteMod;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.io.File;
import java.util.UUID;

public abstract class FabricPlatform implements Platform<ArteMod> {

    @Override
    public File getDataFolder() {
        return FabricLoader.getInstance().getGameDir().resolve("arte").toFile();
    }

    @Override
    public File getConfigFile() {
        return FabricLoader.getInstance().getConfigDir().resolve("arte.json").toFile();
    }

    @Override
    public void applyPack(Object playerObject, UUID id, String address, String hash, boolean force, Object textObject) {
        if (!(playerObject instanceof ServerPlayerEntity player))
            return;

        if (!(textObject instanceof Text text))
            return;

        this.applyPack(player, id, address, hash, force, text);
    }

    protected abstract void applyPack(ServerPlayerEntity player, UUID id, String address, String hash, boolean force, Text text);
}
