package io.shardmc.arte.bukkit.config;

import io.shardmc.arte.common.Arte;
import io.shardmc.arte.common.config.ArteConfigAdapter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Collection;

public class BukkitArteConfigAdapter extends ArteConfigAdapter<ConfigurationSection> {

    public BukkitArteConfigAdapter(Arte arte) {
        super(arte);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T read(ConfigurationSection section, String name) {
        return (T) section.get(name);
    }

    @Override
    protected <T> Collection<T> readList(ConfigurationSection section, String name) {
        return this.read(section, name);
    }

    @Override
    public void write(ConfigurationSection section, String name, Object value) {
        section.set(name, value);
    }

    @Override
    protected boolean has(ConfigurationSection section, String key) {
        return section.contains(key);
    }
}
