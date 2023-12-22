package the.grid.smp.arte.fabric.config;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import the.grid.smp.arte.common.config.ArteConfig;
import the.grid.smp.arte.common.data.PackMode;
import the.grid.smp.arte.common.logger.ArteLogger;
import the.grid.smp.arte.fabric.ArteMod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FabricArteConfig
implements ArteConfig {
    private int port = 8164;
    private String address = "0.0.0.0";
    private boolean scramble = true;
    private boolean repackOnStart = true;
    private PackMode mode = PackMode.BASIC;
    private String prompt = "Hello! Please download the resourcepack!";
    private Set<String> namespaces;
    private boolean whitelist = true;
    private List<List<String>> groups;
    private JSONObject config = new JSONObject();
    private final File file;

    private final ArteLogger logger;

    public FabricArteConfig(ArteMod mod) {
        this.file = mod.getConfigFile();
        this.logger = mod.logger();
    }

    public void reload() {
        try {
            InputStream stream;
            if (!this.file.exists()) {
                stream = this.getClass().getClassLoader().getResourceAsStream("arte.json");
                try {
                    assert (stream != null);
                    Files.copy(stream, this.file.toPath());
                }
                finally {
                    if (stream != null) {
                        stream.close();
                    }
                }
            }
            stream = new FileInputStream(this.file);
            this.config = new JSONObject(new JSONTokener(stream));
            stream.close();
            this.read();
        }
        catch (IOException e) {
            this.logger.throwing(e);
        }
    }

    public void read() {
        this.port = this.config.optInt("port", 8164);
        this.address = this.config.optString("address", "0.0.0.0");
        this.scramble = this.config.optBoolean("scramble", true);
        this.repackOnStart = this.config.optBoolean("repack-on-restart", true);
        this.mode = PackMode.valueOf(this.config.optString("mode", "BASIC"));
        this.prompt = this.config.optString("prompt", "Hello! Please download the resourcepack!");
        this.namespaces = new HashSet<>();
        JSONArray jsonArray1 = this.config.optJSONArray("namespaces", new JSONArray());
        if (jsonArray1 != null) {
            int len = jsonArray1.length();
            for (int i = 0; i < len; ++i) {
                this.namespaces.add(jsonArray1.get(i).toString());
            }
        }
        this.whitelist = this.config.optBoolean("whitelist", true);
        this.groups = new ArrayList<>();
        jsonArray1 = this.config.optJSONArray("groups", new JSONArray());
        if (jsonArray1 != null) {
            for (int i = 0; i < jsonArray1.length(); ++i) {
                JSONArray jsonArray2 = (JSONArray)jsonArray1.get(i);
                List<String> array = new ArrayList<>();
                int j = 0;
                while (j < jsonArray2.length()) {
                    array.add((String)jsonArray2.get(j));
                    ++i;
                }
                this.groups.add(array);
            }
        }
    }

    public int getPort() {
        return this.port;
    }

    public String getAddress() {
        return this.address;
    }

    public boolean scramble() {
        return this.scramble;
    }

    public boolean repackOnStart() {
        return this.repackOnStart;
    }

    public PackMode getMode() {
        return this.mode;
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

    public List<List<String>> getGroups() {
        return this.groups;
    }
}
