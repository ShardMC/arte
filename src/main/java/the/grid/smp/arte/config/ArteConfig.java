package the.grid.smp.arte.config;

import the.grid.smp.arte.Arte;
import the.grid.smp.arte.config.data.PackMode;
import the.grid.smp.communis.config.Config;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"FieldCanBeLocal"})
public class ArteConfig extends Config {

    private final int port = 8164;
    private final boolean scramble = true;
    private final boolean repackOnStart = false;

    private final PackMode mode = PackMode.BASIC;
    private final String prompt = "Hello! Please download the resourcepack!";

    // optionals. if whitelist is true, then the list represents optional packs
    private final Set<String> namespaces = new HashSet<>();
    private final boolean whitelist = true;

    private final String[][] groups = new String[][] {};

    @SuppressWarnings("unused")
    private ArteConfig() {
        this(Arte.getInstance());
    }

    public ArteConfig(Arte arte) {
        super(arte, "config");
    }

    public Integer getPort() {
        return this.port;
    }

    public boolean shouldScramble() {
        return this.scramble;
    }

    public boolean repackOnStart() {
        return repackOnStart;
    }

    public PackMode getMode() {
        return mode;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public Set<String> getNamespaces() {
        return this.namespaces;
    }

    public boolean isWhitelist() {
        return this.whitelist;
    }

    public String[][] getGroups() {
        return this.groups;
    }
}
