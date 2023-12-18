package the.grid.smp.arte.common.zip;

import org.apache.commons.compress.archivers.zip.ParallelScatterZipCreator;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import the.grid.smp.arte.common.util.Util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.zip.ZipEntry;

public class Zip implements AutoCloseable {

    private final ParallelScatterZipCreator scatter = new ParallelScatterZipCreator();

    private final Path root;
    private final Path output;
    private final boolean scramble;

    public Zip(Path root, Path output, boolean scramble) throws IOException {
        this.root = root;
        this.output = output;
        this.scramble = scramble;
    }

    public void add(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            Util.walk(path, (file, attrs) -> {
                if (!attrs.isDirectory()) {
                    try {
                        this.addFile(file);
                    } catch (IOException e) {
                        System.out.println("Failed to process " + path);
                        throw new RuntimeException(e);
                    }
                }

                return FileVisitResult.CONTINUE;
            });

            return;
        }

        this.addFile(path);
    }

    protected void addFile(Path path) throws IOException {
        ZipArchiveEntry entry = new ZipArchiveEntry(this.root.relativize(path).toString());
        entry.setMethod(ZipEntry.DEFLATED);

        this.scatter.addArchiveEntry(entry, () -> {
            try {
                return Files.newInputStream(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        if (this.scramble) {
            Scrambler.scramble(entry);
        }
    }

    public void add(Collection<Path> paths) throws IOException {
        for (Path path : paths) {
            this.add(path);
        }
    }

    @Override
    public void close() throws IOException, ExecutionException, InterruptedException {
        try (ZipArchiveOutputStream zos = new ZipArchiveOutputStream(Files.newOutputStream(this.output))) {
            this.scatter.writeTo(zos);
        }

        if (this.scramble) {
            Scrambler.scramble(this.output);
        }
    }
}
