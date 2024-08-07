package su.shardmc.arte.common.pack.zipper;

import su.shardmc.arte.common.config.data.FilterList;
import su.shardmc.arte.common.logger.ArteLogger;
import su.shardmc.arte.common.zip.Zip;
import su.shardmc.arte.common.pack.meta.BuiltPack;
import su.shardmc.arte.common.pack.meta.file.BasicPackFile;
import su.shardmc.arte.common.pack.meta.file.Namespace;
import su.shardmc.arte.common.pack.meta.file.PackFile;
import su.shardmc.arte.common.util.ThreadPool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @implNote This class should ALWAYS be re-instantiated for every reload.
 */
public abstract class PackZipper {

    protected final Path root;
    protected final Path output;

    protected final Path assets;
    protected final ArteLogger logger;

    protected Collection<BuiltPack> packs = new ArrayList<>();

    public PackZipper(ArteLogger logger, Path root, Path output) throws IOException {
        this.root = Files.createDirectories(root);
        this.output = Files.createDirectories(output);

        this.assets = Files.createDirectories(this.root.resolve("assets"));
        this.logger = logger;
    }

    protected Context createContext() {
        return new Context(this.logger, this.root, this.output, "pack.mcmeta", "pack.png");
    }

    public void zip(FilterList list, boolean scramble, Consumer<BuiltPack> consumer) {
        this.logger.info("Pack zipper started re-zipping!");
        Context context = this.createContext();
        this.zip(context);

        this.packs = context.zip(list, scramble, consumer);
    }

    public void clean() throws IOException {
        try (Stream<Path> stream = Files.list(this.output)) {
            stream.parallel().forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        this.packs.clear();
    }

    public Collection<BuiltPack> getPacks() {
        return packs;
    }

    protected abstract void zip(Context context);

    public static class Context {

        protected final ArteLogger logger;

        protected final Path root;
        protected final Path output;

        protected final List<Namespace> groups = new ArrayList<>();
        protected final List<PackFile> defaults = new ArrayList<>();

        public Context(ArteLogger logger, Path root, Path output, String... defaults) {
            this.logger = logger;

            this.root = root;
            this.output = output;

            for (String path : defaults) {
                this.defaults.add(new BasicPackFile(root.resolve(path)));
            }
        }

        public Context addNamespace(String name, Collection<Path> files) {
            this.groups.add(new Namespace(this.root, name, files));
            return this;
        }

        public Collection<BuiltPack> zip(FilterList list, boolean scramble, Consumer<BuiltPack> consumer) {
            List<BuiltPack> packs = new ArrayList<>();
            ThreadPool pool = new ThreadPool(this.logger);

            for (Namespace group : this.groups) {
                pool.add(() -> {
                    try {
                        Path generated = this.output.resolve(group.name() + ".zip");
                        try (Zip zip = new Zip(this.root, generated, scramble)) {
                            group.zip(zip);

                            for (PackFile file : this.defaults) {
                                if (!group.files().contains(group.getPath().resolve(file.getPath().getFileName())))
                                    file.zip(zip);
                            }
                        }

                        boolean force = !(list.elements().contains(
                                group.name())
                        ) && list.whitelist();

                        BuiltPack pack = new BuiltPack(generated, force);

                        consumer.accept(pack);
                        packs.add(pack);
                    } catch (IOException | ExecutionException | InterruptedException e) {
                        this.logger.throwing(e, "Failed to zip pack part: '" + group + "'");
                    }
                });
            }

            pool.start();
            return packs;
        }
    }
}
