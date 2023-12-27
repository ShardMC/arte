package the.grid.smp.arte.common.pack.meta.file;

import the.grid.smp.arte.common.rewrite.Zip;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public record NamespaceGroup(String name, Collection<Path> namespaces) implements PackFile {

    public NamespaceGroup(String name, Path... namespaces) {
        this(name, List.of(namespaces));
    }

    @Override
    public void zip(Zip zip) throws IOException {
        zip.addDirectories(this.namespaces);
    }
}
