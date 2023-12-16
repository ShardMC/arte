package the.grid.smp.arte.pack.meta;

import the.grid.smp.arte.zip.Zip;

import java.io.IOException;
import java.nio.file.Path;

public record BasicPackFile(Path path) implements PackFile {

    @Override
    public void zip(Zip zip) throws IOException {
        zip.add(this.path);
    }
}
