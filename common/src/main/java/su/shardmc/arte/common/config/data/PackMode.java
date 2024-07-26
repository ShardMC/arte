package su.shardmc.arte.common.config.data;

import su.shardmc.arte.common.pack.zipper.impl.BasicPackZipper;
import su.shardmc.arte.common.pack.zipper.impl.CachedPackZipper;
import su.shardmc.arte.common.util.lambda.PackZipperCreator;

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
