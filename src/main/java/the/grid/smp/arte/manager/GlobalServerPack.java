package the.grid.smp.arte.manager;

import the.grid.smp.arte.util.ConsumerIO;
import the.grid.smp.arte.util.ThreadPool;
import the.grid.smp.arte.util.Util;
import the.grid.smp.arte.zip.Zip;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GlobalServerPack {

    private final Logger logger;

    private final Path root;
    private final Path generated;

    public GlobalServerPack(Logger logger, Path root, Path generated) {
        this.logger = logger;

        this.root = root;
        this.generated = generated;
    }

    public void rezip(String[][] groups, boolean scramble) throws IOException {
        Files.createDirectories(this.root);
        Files.createDirectories(this.generated);

        Path assets = this.getAssets();
        Files.createDirectories(assets);

        ThreadPool pool = new ThreadPool();

        try (Stream<Path> stream = Files.list(assets)) {
            Map<String, Path> namespaces = stream.parallel().collect(Collectors.toMap(Util::getName, path -> path));

            for (String[] group : groups) {
                Path[] grouped = new Path[group.length];

                for (int i = 0; i < group.length; i++) {
                    Path path = namespaces.remove(group[i]);

                    if (path == null)
                        continue;

                    grouped[i] = path;
                }

                this.logger.info("Grouped " + Arrays.toString(grouped) + "!");
                pool.add(() -> this.tryRezip(scramble, grouped));
            }

            for (Path path : namespaces.values()) {
                pool.add(() -> this.tryRezip(scramble, path));
            }
        }

        pool.start();
    }

    private void tryRezip(boolean scramble, Path... namespaces) {
        try {
            this.rezip(scramble, namespaces);
        } catch (IOException e) {
            this.logger.severe("Failed to re-zip namespace(s) " + String.join(", ", Util.getName(namespaces)));
            e.printStackTrace();
        }
    }

    private void rezip(boolean scramble, Path... namespaces) throws IOException {
        long start = System.currentTimeMillis();
        String[] names = Util.getName(namespaces);

        Path name = this.generated.resolve(String.join("+", names) + ".zip");
        Zip zip = new Zip(this.root, name, scramble);

        Path meta = this.root.resolve("pack.mcmeta");

        if (Files.exists(meta)) {
            zip.add(meta);
        }

        Path icon = this.root.resolve("pack.png");

        if (Files.exists(icon)) {
            zip.add(icon);
        }

        zip.add(namespaces);
        zip.close();

        this.logger.info("Zipped " + String.join(", ", names) + " in " + (System.currentTimeMillis() - start) + "ms!");
    }

    public void collect(Collection<String> namespaces, boolean whitelist, ConsumerIO<ResourcePack> consumer) throws IOException {
        long start = System.currentTimeMillis();
        Util.walk(this.generated, (file, attrs) -> {
            boolean force = whitelist && !(namespaces.contains(
                    Util.getNameWithoutExtension(file))
            );

            consumer.accept(new ResourcePack(file, force));
            return FileVisitResult.CONTINUE;
        });

        this.logger.info("Collected in " + (System.currentTimeMillis() - start) + "ms!");
    }

    public Path getRoot() {
        return root;
    }

    public Path getGenerated() {
        return generated;
    }

    public Path getAssets() {
        return this.root.resolve("assets");
    }
}
