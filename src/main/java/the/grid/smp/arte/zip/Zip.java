package the.grid.smp.arte.zip;

import the.grid.smp.arte.util.Util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip implements AutoCloseable {

    private final Path root;
    private final Path output;
    private final ZipOutputStream zos;
    private final boolean scramble;

    public Zip(Path root, Path output, boolean scramble) throws FileNotFoundException {
        this.root = root;
        this.output = output;
        this.scramble = scramble;

        this.zos = new ZipOutputStream(new FileOutputStream(output.toFile()));
    }

    public void add(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            Util.walk(path, (file, attrs) -> {
                this.add(file);
                return FileVisitResult.CONTINUE;
            });

            return;
        }

        ZipEntry entry = new ZipEntry(this.root.relativize(path).toString());
        this.zos.putNextEntry(entry);

        Files.copy(path, this.zos);
        this.zos.closeEntry();

        if (this.scramble) {
            Scrambler.scramble(entry);
        }
    }

    @Override
    public void close() throws IOException {
        this.zos.close();

        if (this.scramble) {
            Scrambler.scramble(this.output);
        }
    }

    public Path getOutput() {
        return output;
    }
}
