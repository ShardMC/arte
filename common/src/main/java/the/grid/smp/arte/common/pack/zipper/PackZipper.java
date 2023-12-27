package the.grid.smp.arte.common.pack.zipper;

import the.grid.smp.arte.common.data.FilterList;
import the.grid.smp.arte.common.logger.ArteLogger;
import the.grid.smp.arte.common.pack.meta.file.BasicPackFile;
import the.grid.smp.arte.common.pack.meta.BuiltPack;
import the.grid.smp.arte.common.pack.meta.file.Namespace;
import the.grid.smp.arte.common.pack.meta.file.PackFile;
import the.grid.smp.arte.common.pack.meta.file.NamespaceGroup;
import the.grid.smp.arte.common.rewrite.Zip;
import the.grid.smp.arte.common.util.ThreadPool;

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

    public void zip(FilterList list, boolean scramble, Consumer<BuiltPack> consumer) throws IOException {
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

    protected abstract void zip(Context context) throws IOException;

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

        public Context addNamespace(Path... namespaces) {
            /*StringBuilder builder = new StringBuilder();

            for (Path path : namespaces) {
                builder.append(path.getFileName().toString());
            }

            this.groups.add(new NamespaceGroup(builder.toString(), namespaces));
            this.logger.info("Adding namespace(s) to zip: '", builder.toString(), "'");*/
            return this;
        }

        public Context addNamespace(String name, Collection<Path> files) {
            this.groups.add(new Namespace(name, files));
            this.logger.info("Adding namespace(s) to zip: '", name, "'");
            return this;
        }

        public Collection<BuiltPack> zip(FilterList list, boolean scramble, Consumer<BuiltPack> consumer) throws IOException {
            List<BuiltPack> packs = new ArrayList<>();
            ThreadPool pool = new ThreadPool(this.logger);

            for (Namespace group : this.groups) {
                pool.add(() -> {
                    try {
                        Path generated = this.output.resolve(group.name() + ".zip");
                        this.logger.info("Zipping pack to", generated.toString());

                        long start = System.currentTimeMillis();
                        try (Zip zip = new Zip(this.logger, this.root, generated, false)) {
                            group.zip(zip);

                            for (PackFile file : this.defaults) {
                                file.zip(zip);
                            }
                        }

                        System.out.println(System.currentTimeMillis() - start + " - time to zip!");

                        boolean force = !(list.elements().contains(
                                group.name())
                        ) && list.whitelist();

                        start = System.currentTimeMillis();
                        BuiltPack pack = new BuiltPack(generated, force);
                        System.out.println(System.currentTimeMillis() - start + " - time to create builtpack!");

                        consumer.accept(pack);
                        packs.add(pack);
                    } catch (IOException | ExecutionException | InterruptedException e) {

                        this.logger.throwing(e, "Uh-oh. This shouldn't happen!");
                    }
                });
            }
            /*Path assets = this.root.resolve("assets");
            try (Stream<Path> stream = Files.list(assets)) {
                stream.forEach(path -> pool.add(() -> {
                    String name = path.getFileName().toString() + ".zip";
                    try (Zip zip = new Zip(this.logger, this.root, this.output.resolve(name), false)) {
                        zip.addDirectory(path);
                    } catch (IOException | ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }));
            }*/

            pool.start();
            return packs;
        }
    }
}
