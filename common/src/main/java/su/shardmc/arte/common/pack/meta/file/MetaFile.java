package su.shardmc.arte.common.pack.meta.file;

import su.shardmc.arte.common.zip.Zip;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public record MetaFile(Path path) implements PackFile {

    private static final Format FORMAT = new SimpleDateFormat("yyMMdd");

    @Override
    public void zip(Zip zip) {
        String date = FORMAT.format(Date.from(Instant.now()));

        try {
            String meta = Files.readString(this.path).replace("%DATE%", date);
            zip.addFile(this.path, new ByteArrayInputStream(meta.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Path getPath() {
        return this.path;
    }
}
