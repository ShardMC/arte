package the.grid.smp.arte.common.data;

import the.grid.smp.arte.common.pack.zipper.impl.BasicPackZipper;
import the.grid.smp.arte.common.pack.zipper.impl.CachedPackZipper;
import the.grid.smp.arte.common.util.lambda.PackZipperCreator;

@SuppressWarnings("unused")
public enum PackMode {
    BASIC(BasicPackZipper::new),
    CACHED(CachedPackZipper::new);

    private final PackZipperCreator creator;

    PackMode(PackZipperCreator creator) {
        this.creator = creator;
    }

    public PackZipperCreator creator() {
        return this.creator;
    }
}
