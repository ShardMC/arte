package the.grid.smp.arte.bukkit.config;

import org.bukkit.configuration.ConfigurationSection;
import the.grid.smp.arte.bukkit.ArtePlugin;
import the.grid.smp.arte.common.config.ArteConfig;
import the.grid.smp.arte.common.data.PackMode;
import the.grid.smp.communis.config.Config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BukkitArteConfig extends Config implements ArteConfig {

    private int port;
    private String address;

    private boolean scramble;
    private boolean repackOnStart;

    private PackMode mode;
    private String prompt;

    // optionals. if whitelist is true, then the list represents optional packs
    private Set<String> namespaces;
    private boolean whitelist;

    private List<List<String>> groups;

    public BukkitArteConfig(ArtePlugin arte) {
        super(arte, "config");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void read(ConfigurationSection section) {
        this.port = section.getInt("port", 8164);
        this.address = section.getString("address", "0.0.0.0");

        this.scramble = section.getBoolean("scramble", true);
        this.repackOnStart = section.getBoolean("repack-on-restart", true);

        this.mode = PackMode.valueOf(section.getString("mode", "BASIC"));
        this.prompt = section.getString("prompt", "Hello! Please download the resourcepack!");

        this.namespaces = new HashSet<>(section.getStringList("namespaces"));
        this.whitelist = section.getBoolean("whitelist", true);

        this.groups = (List<List<String>>) section.getList("groups", new ArrayList<>());
    }

    @Override
    public void write(ConfigurationSection section) {
        section.set("port", this.port);
        section.set("address", this.address);

        section.set("scramble", this.scramble);
        section.set("repack-on-restart", this.repackOnStart);

        section.set("mode", this.mode.toString());
        section.set("prompt", this.prompt);

        section.set("namespaces", this.namespaces);
        section.set("whitelist", this.whitelist);

        section.set("groups", this.groups);
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public PackMode getMode() {
        return mode;
    }

    @Override
    public boolean scramble() {
        return scramble;
    }

    @Override
    public boolean repackOnStart() {
        return repackOnStart;
    }

    @Override
    public String getPrompt() {
        return prompt;
    }

    @Override
    public Set<String> getNamespaces() {
        return namespaces;
    }

    @Override
    public boolean isWhitelist() {
        return whitelist;
    }

    @Override
    public List<List<String>> getGroups() {
        return groups;
    }
}
