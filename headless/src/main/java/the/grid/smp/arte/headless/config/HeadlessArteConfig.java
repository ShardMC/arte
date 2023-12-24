package the.grid.smp.arte.headless.config;

import the.grid.smp.arte.common.config.ArteConfig;
import the.grid.smp.arte.common.data.PackMode;
import the.grid.smp.arte.headless.ArteHeadless;

import java.util.ArrayList;
import java.util.HashSet;

public class HeadlessArteConfig extends ArteConfig {

    public HeadlessArteConfig(ArteHeadless arte) {
        super(arte.logger(), arte.getDataFolder().toPath().resolve("config.yml"), false);

        this.port = 1864;
        this.address = "0.0.0.0";

        this.scramble = true;
        this.mode = PackMode.BASIC;
        this.prompt = "Hello! Please download the resourcepack!";

        this.namespaces = new HashSet<>();
        this.whitelist = true;

        this.groups = new ArrayList<>();
    }

    @Override
    protected void read() { }

    @Override
    protected void write() { }

    @Override
    protected void defaults() { }

    @Override
    protected void create() { }

    @Override
    protected void dump() { }
}
