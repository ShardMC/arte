package the.grid.smp.arte.fabric.config;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import the.grid.smp.arte.common.config.ArteConfig;
import the.grid.smp.arte.common.data.PackMode;
import the.grid.smp.arte.common.util.Util;
import the.grid.smp.arte.fabric.ArteMod;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class FabricArteConfig extends ArteConfig {

    private JSONObject config;
    private JSONObject defaults;

    public FabricArteConfig(ArteMod mod) {
        super(mod.logger(), mod.getConfigFile());
    }

    private void read(String name, BiConsumer<JSONObject, String> consumer) {
        if (this.config.has(name)) {
            consumer.accept(this.config, name);
            return;
        }

        consumer.accept(this.defaults, name);
    }

    private static <T> void copy(Collection<T> collection, JSONArray array, Function<Object, T> map) {
        for (Object obj : array) {
            collection.add(map.apply(obj));
        }
    }

    private static <T> List<T> asList(JSONArray array, Function<Object, T> map) {
        List<T> list = new ArrayList<>();
        FabricArteConfig.copy(list, array, map);

        return list;
    }

    private static List<String> asList(JSONArray array) {
        return FabricArteConfig.asList(array, Object::toString);
    }

    private static <T> Set<T> asSet(JSONArray array, Function<Object, T> map) {
        Set<T> set = new HashSet<>();
        FabricArteConfig.copy(set, array, map);

        return set;
    }

    private static Set<String> asSet(JSONArray array) {
        return FabricArteConfig.asSet(array, Object::toString);
    }

    @Override
    public void read() {
        this.read("port", (obj, s) -> this.port = obj.getInt(s));
        this.read("address", (obj, s) -> this.address = obj.getString(s));

        this.read("scramble", (obj, s) -> this.scramble = obj.getBoolean(s));
        this.read("repack-on-start", (obj, s) -> this.repackOnStart = obj.getBoolean(s));

        this.read("mode", (obj, s) -> this.mode = obj.getEnum(PackMode.class, s));
        this.read("prompt", (obj, s) -> this.prompt = obj.getString(s));

        this.read("namespaces", (obj, s) -> this.namespaces = FabricArteConfig.asSet(obj.getJSONArray(s)));
        this.read("whitelist", (obj, s) -> this.whitelist = obj.getBoolean(s));

        this.read("groups", (obj, s) -> {
            JSONArray array = obj.getJSONArray(s);
            List<List<String>> groups = new ArrayList<>();

            for (Object object : array) {
                groups.add(FabricArteConfig.asList((JSONArray) object));
            }

            this.groups = groups;
        });
    }

    @Override
    protected void write() throws IOException {
        this.config.put("port", this.port);
        this.config.put("address", this.address);

        this.config.put("scramble", this.scramble);
        this.config.put("repack-on-restart", this.repackOnStart);

        this.config.put("mode", this.mode.toString());
        this.config.put("prompt", this.prompt);

        this.config.put("namespaces", this.namespaces);
        this.config.put("whitelist", this.whitelist);

        this.config.put("groups", this.groups);
    }

    @Override
    protected void defaults() throws IOException {
        try (InputStream stream = Util.getDefaultResourceStream(this.file)) {
            this.defaults = new JSONObject(new JSONTokener(stream));
        }
    }

    @Override
    protected void create() throws IOException {
        try (InputStream stream = Files.newInputStream(this.file)) {
            this.config = new JSONObject(new JSONTokener(stream));
        }
    }

    @Override
    protected void dump() throws IOException {
        try (Writer writer = Files.newBufferedWriter(this.file)) {
            this.config.write(writer);
        }
    }
}
