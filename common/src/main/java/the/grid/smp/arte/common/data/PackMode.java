package the.grid.smp.arte.common.data;

import the.grid.smp.arte.common.pack.zipper.BasicPackZipper;
import the.grid.smp.arte.common.pack.zipper.CachedPackZipper;
import the.grid.smp.arte.common.pack.zipper.grouped.ManualGroupedPackZipper;
import the.grid.smp.arte.common.util.lambda.PackZipperCreator;

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
