package io.shardmc.arte.fabric.config;

import io.shardmc.arte.common.Arte;
import io.shardmc.arte.common.config.ArteConfigAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FabricArteConfigAdapter extends ArteConfigAdapter<JSONObject> {

    public FabricArteConfigAdapter(Arte arte) {
        super(arte);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T read(JSONObject obj, String name) {
        return (T) obj.get(name);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> Collection<T> readList(JSONObject object, String name) {
        List<T> collection = new ArrayList<>();
        JSONArray array = object.getJSONArray(name);

        for (Object o : array) {
            collection.add((T) o);
        }

        return collection;
    }

    @Override
    protected void write(JSONObject obj, String name, Object value) {
        obj.put(name, value);
    }

    @Override
    protected boolean has(JSONObject obj, String key) {
        return obj.has(key);
    }
}
