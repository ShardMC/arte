package the.grid.smp.arte.common.rewrite;

import org.apache.commons.compress.archivers.zip.ParallelScatterZipCreator;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import the.grid.smp.arte.common.logger.ArteLogger;
import the.grid.smp.arte.common.util.ThreadPool;
import the.grid.smp.arte.common.util.Util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.zip.ZipEntry;

public class Zip implements AutoCloseable {

    private final ParallelScatterZipCreator scatter = new ParallelScatterZipCreator();
    private final ThreadPool pool;
    private final ArteLogger logger;

    private final Path root;
    private final Path output;
    private final boolean scramble;

    public Zip(ArteLogger logger, Path root, Path output, boolean scramble) {
        this.root = root;
        this.output = output;
        this.scramble = scramble;

        this.pool = new ThreadPool(logger);
        this.logger = logger;
    }

    public void addDirectory(Path path) throws IOException {
        Util.walk(this.logger, path, this::addFile);
    }

    public void addFile(Path path) {
        ZipArchiveEntry entry = new ZipArchiveEntry(this.root.relativize(path).toString());
        entry.setMethod(ZipEntry.DEFLATED);

        this.scatter.addArchiveEntry(entry, () -> {
            try {
                return new BufferedInputStream(new FileInputStream(path.toFile()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        if (this.scramble) {
            Scrambler.scramble(entry);
        }
    }

    public void addFiles(Collection<Path> paths) {
        for (Path path : paths) {
            this.addFile(path);
        }
    }

    public void addDirectories(Collection<Path> paths) throws IOException {
        for (Path path : paths) {
            this.addDirectory(path);
        }
    }

    @Override
    public void close() throws IOException, ExecutionException, InterruptedException {
        this.pool.start();

        try (ZipArchiveOutputStream zos = new ZipArchiveOutputStream(this.output)) {
            this.scatter.writeTo(zos);
        }

        if (this.scramble) {
            Scrambler.scramble(this.output);
        }
    }
}
