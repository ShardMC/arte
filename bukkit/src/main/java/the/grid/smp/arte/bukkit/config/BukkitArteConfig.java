package the.grid.smp.arte.bukkit.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import the.grid.smp.arte.bukkit.ArtePlugin;
import the.grid.smp.arte.common.config.ArteConfig;
import the.grid.smp.arte.common.data.PackMode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;

public class BukkitArteConfig extends ArteConfig {

    private final ArtePlugin arte;
    private FileConfiguration config;

    public BukkitArteConfig(ArtePlugin arte) {
        super(arte.logger(), arte.getDataFolder().toPath().resolve("config.yml"), false);
        this.arte = arte;

        this.reload();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void read() {
        this.port = this.config.getInt("port");
        this.address = this.config.getString("address");

        this.prompt = this.config.getString("prompt");
        this.scramble = this.config.getBoolean("scramble");
        this.mode = PackMode.valueOf(this.config.getString("mode"));

        this.namespaces = new HashSet<>(this.config.getStringList("namespaces"));
        this.whitelist = this.config.getBoolean("whitelist");

        this.groups = (List<List<String>>) this.config.getList("groups");
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

        this.config.set("groups", this.groups);
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

    @Override
    protected InputStream getResource(Path path) {
        return this.arte.getResourceStream(path.getFileName().toString());
    }

    @Override
    protected File getResourceFile(Path path) throws IOException {
        return this.arte.getResourceFile(path.getFileName().toString());
    }
}
