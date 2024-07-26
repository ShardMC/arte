package su.shardmc.arte.common.zip;

import org.apache.commons.compress.archivers.zip.ParallelScatterZipCreator;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.*;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.zip.ZipEntry;

public class Zip implements AutoCloseable {

    private final ParallelScatterZipCreator scatter = new ParallelScatterZipCreator();

    private final Path root;
    private final Path output;
    private final boolean scramble;

    public Zip(Path root, Path output, boolean scramble) {
        this.root = root;
        this.output = output;
        this.scramble = scramble;
    }

    public void addFile(Path path) {
        this.addFile(path, path);
    }

    public void addFile(Path path, Path pathZipped) {
        try {
            this.addFile(pathZipped, new FileInputStream(path.toFile()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addFile(Path path, InputStream stream) {
        ZipArchiveEntry entry = new ZipArchiveEntry(this.root.relativize(path).toString());
        entry.setMethod(ZipEntry.DEFLATED);

        this.scatter.addArchiveEntry(entry, () -> new BufferedInputStream(stream));

        if (this.scramble) {
            Scrambler.scramble(entry);
        }
    }

    @Override
    public void close() throws IOException, ExecutionException, InterruptedException {
        try (ZipArchiveOutputStream zos = new ZipArchiveOutputStream(this.output)) {
            this.scatter.writeTo(zos);
        }

        if (this.scramble) {
            Scrambler.scramble(this.output);
        }
    }
}
