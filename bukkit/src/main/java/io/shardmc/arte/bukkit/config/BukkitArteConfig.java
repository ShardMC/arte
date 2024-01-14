package io.shardmc.arte.bukkit.config;

import io.shardmc.arte.bukkit.ArtePlugin;
import io.shardmc.arte.common.config.ArteConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

public class BukkitArteConfig extends ArteConfig<ConfigurationSection> {

    public BukkitArteConfig(ArtePlugin arte) {
        super(arte);
    }

    @Override
    protected BukkitArteConfigAdapter serializer() {
        return new BukkitArteConfigAdapter(this.arte);
    }

    @Override
    protected ConfigurationSection defaults() throws IOException {
        return YamlConfiguration.loadConfiguration(this.getResourceFile(this.file));
    }

    @Override
    protected ConfigurationSection create() {
        return YamlConfiguration.loadConfiguration(this.file.toFile());
    }

    @Override
    protected void dump(ConfigurationSection section) throws IOException {
        if (section instanceof FileConfiguration config)
            config.save(this.file.toFile());
    }
}
