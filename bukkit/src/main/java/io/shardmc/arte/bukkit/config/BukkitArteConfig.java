package io.shardmc.arte.bukkit.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import io.shardmc.arte.bukkit.ArtePlugin;
import io.shardmc.arte.common.config.ArteConfig;
import io.shardmc.arte.common.data.PackMode;

import java.io.IOException;
import java.util.HashSet;

public class BukkitArteConfig extends ArteConfig {

    private FileConfiguration config;

    public BukkitArteConfig(ArtePlugin arte) {
        super(arte);
    }

    @Override
    protected void read() {
        this.port = this.config.getInt("port");
        this.address = this.config.getString("address");

        this.prompt = this.config.getString("prompt");
        this.scramble = this.config.getBoolean("scramble");
        this.mode = PackMode.valueOf(this.config.getString("mode"));

        this.namespaces = new HashSet<>(this.config.getStringList("namespaces"));
        this.whitelist = this.config.getBoolean("whitelist");
    }

    @Override
    protected void write() {
        this.config.set("port", this.port);
        this.config.set("address", this.address);

        this.config.set("prompt", this.prompt);
        this.config.set("scramble", this.scramble);
        this.config.set("mode", this.mode.toString());

        this.config.set("namespaces", this.namespaces);
        this.config.set("whitelist", this.whitelist);
    }

    @Override
    protected void defaults() throws IOException {
        FileConfiguration defaults = YamlConfiguration.loadConfiguration(this.getResourceFile(this.file));
        this.config.setDefaults(defaults);
    }

    @Override
    protected void create() {
        this.config = YamlConfiguration.loadConfiguration(this.file.toFile());
    }

    @Override
    protected void dump() throws IOException {
        this.config.save(this.file.toFile());
    }
}
