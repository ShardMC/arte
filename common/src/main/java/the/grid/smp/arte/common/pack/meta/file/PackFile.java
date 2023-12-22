package the.grid.smp.arte.common.pack.meta.file;

import the.grid.smp.arte.common.zip.Zip;

import java.io.IOException;

public interface PackFile {

    void zip(Zip zip) throws IOException;
}
