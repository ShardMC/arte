package the.grid.smp.arte.config.data;

import the.grid.smp.arte.pack.zipper.BasicPackZipper;
import the.grid.smp.arte.pack.zipper.CachedPackZipper;
import the.grid.smp.arte.pack.zipper.grouped.ManualGroupedPackZipper;
import the.grid.smp.arte.util.lambda.PackZipperCreator;

public enum PackMode {
    BASIC(BasicPackZipper::new),
    MANUAL(ManualGroupedPackZipper::new),
    CACHED(CachedPackZipper::new);


    private final PackZipperCreator creator;

    PackMode(PackZipperCreator creator) {
        this.creator = creator;
    }

    public PackZipperCreator creator() {
        return this.creator;
    }
}
