package the.grid.smp.arte.common.pack.zipper;

import the.grid.smp.arte.common.data.FilterList;
import the.grid.smp.arte.common.logger.ArteLogger;
import the.grid.smp.arte.common.pack.meta.BasicPackFile;
import the.grid.smp.arte.common.pack.meta.BuiltPack;
import the.grid.smp.arte.common.pack.meta.PackFile;
import the.grid.smp.arte.common.pack.meta.namespace.NamespaceGroup;
import the.grid.smp.arte.common.pack.meta.namespace.NamespaceLike;
import the.grid.smp.arte.common.util.ThreadPool;
import the.grid.smp.arte.common.zip.Zip;

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
        return new Context(this.root, this.output, "pack.mcmeta", "pack.png");
    }

    public void zip(FilterList list, boolean scramble, Consumer<BuiltPack> consumer) throws IOException {
        Context context = this.createContext();
        this.zip(context);

        this.packs = context.zip(list, scramble, consumer);
    }

    public void clear() throws IOException {
        try (Stream<Path> stream = Files.list(this.output)) {
            stream.parallel().forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public Collection<BuiltPack> getPacks() {
        return packs;
    }

    protected abstract void zip(Context context) throws IOException;

    public static class Context {

        protected final Path root;
        protected final Path output;

        protected final List<NamespaceLike> namespaces = new ArrayList<>();
        protected final List<PackFile> defaults = new ArrayList<>();

        public Context(Path root, Path output, String... defaults) {
            this.root = root;
            this.output = output;

            for (String path : defaults) {
                this.defaults.add(new BasicPackFile(root.resolve(path)));
            }
        }

        public Context addNamespace(Path... namespaces) {
            StringBuilder builder = new StringBuilder();

            for (Path path : namespaces) {
                builder.append(path.getFileName().toString());
            }

            this.namespaces.add(new NamespaceGroup(builder.toString(), namespaces));
            return this;
        }

        public Collection<BuiltPack> zip(FilterList list, boolean scramble, Consumer<BuiltPack> consumer) {
            List<BuiltPack> packs = new ArrayList<>();
            ThreadPool pool = new ThreadPool();

            for (NamespaceLike namespace : this.namespaces) {
                pool.addCatchable(() -> {
                    try {
                        Path generated = this.output.resolve(namespace.name() + ".zip");

                        try (Zip zip = new Zip(this.root, generated, scramble)) {
                            namespace.zip(zip);

                            for (PackFile file : this.defaults) {
                                file.zip(zip);
                            }
                        }

                        boolean force = !(list.elements().contains(
                                namespace.name())
                        ) && list.whitelist();

                        BuiltPack pack = new BuiltPack(generated, force);

                        consumer.accept(pack);
                        packs.add(pack);
                    } catch (IOException | ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            pool.start();
            return packs;
        }
    }
}
