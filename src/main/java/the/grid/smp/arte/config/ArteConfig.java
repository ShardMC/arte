package the.grid.smp.arte.config;

import the.grid.smp.arte.Arte;
import the.grid.smp.communis.config.Config;

@SuppressWarnings({"FieldCanBeLocal"})
public class ArteConfig extends Config {

    private final Integer port = 8164;
    private final boolean scramble = true;
    private final String prompt = "Hello! Please download the resourcepack!";
    private final boolean force = true;

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

    public String getPrompt() {
        return prompt;
    }

    public boolean force() {
        return force;
    }
}
