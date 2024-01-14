package io.shardmc.arte.fabric.config;

import io.shardmc.arte.common.config.ArteConfig;
import io.shardmc.arte.fabric.ArteMod;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;

public class FabricArteConfig extends ArteConfig<JSONObject> {

    public FabricArteConfig(ArteMod mod) {
        super(mod);
    }

    @Override
    protected JSONObject defaults() throws IOException {
        try (InputStream stream = this.getResource(this.file)) {
            return new JSONObject(new JSONTokener(stream));
        }
    }

    @Override
    protected JSONObject create() throws IOException {
        try (InputStream stream = Files.newInputStream(this.file)) {
            return new JSONObject(new JSONTokener(stream));
        }
    }

    @Override
    protected void dump(JSONObject obj) throws IOException {
        try (Writer writer = Files.newBufferedWriter(this.file)) {
            obj.write(writer);
        }
    }

    @Override
    protected FabricArteConfigAdapter serializer() {
        return new FabricArteConfigAdapter(this.arte);
    }
}
