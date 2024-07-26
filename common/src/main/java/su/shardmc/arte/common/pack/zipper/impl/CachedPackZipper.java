package su.shardmc.arte.common.pack.zipper.impl;

import su.shardmc.arte.common.config.data.FilterList;
import su.shardmc.arte.common.logger.ArteLogger;
import su.shardmc.arte.common.Arte;
import su.shardmc.arte.common.pack.meta.BuiltPack;
import su.shardmc.arte.common.pack.meta.file.Namespace;
import su.shardmc.arte.common.util.ThreadPool;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class CachedPackZipper extends BasicPackZipper {

    public CachedPackZipper(Arte arte, Path root, Path output) throws IOException {
        super(arte, root, output);
    }

    @Override
    protected Context createContext() {
        return new CachedContext(this.logger, this.root, this.output, "pack.mcmeta", "pack.png");
    }

    public static class CachedContext extends Context {

        public CachedContext(ArteLogger logger, Path root, Path output, String... defaults) {
            super(logger, root, output, defaults);
        }

        @Override
        public Collection<BuiltPack> zip(FilterList list, boolean scramble, Consumer<BuiltPack> consumer) {
            List<BuiltPack> packs = new ArrayList<>();
            ThreadPool pool = new ThreadPool(this.logger);

            for (Namespace namespace : this.groups) {
                pool.add(() -> {
                    try {
                        Path generated = this.output.resolve(namespace.name() + ".zip");

                        boolean force = !(list.elements().contains(
                                namespace.name())
                        ) && list.whitelist();

                        BuiltPack pack = new BuiltPack(generated, force);

                        consumer.accept(pack);
                        packs.add(pack);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            pool.start();
            return packs;
        }
    }
}
