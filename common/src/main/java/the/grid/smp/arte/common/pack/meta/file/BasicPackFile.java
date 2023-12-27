package the.grid.smp.arte.common.pack.meta.file;

import the.grid.smp.arte.common.zip.Zip;

import java.nio.file.Path;

public record BasicPackFile(Path path) implements PackFile {

    @Override
    public void zip(Zip zip) {
        zip.addFile(this.path);
    }
}
