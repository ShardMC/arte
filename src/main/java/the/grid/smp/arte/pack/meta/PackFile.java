package the.grid.smp.arte.pack.meta;

import the.grid.smp.arte.zip.Zip;

import java.io.IOException;

public interface PackFile {

    void zip(Zip zip) throws IOException;
}
