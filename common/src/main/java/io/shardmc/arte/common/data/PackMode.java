package io.shardmc.arte.common.data;

import io.shardmc.arte.common.pack.zipper.impl.BasicPackZipper;
import io.shardmc.arte.common.pack.zipper.impl.CachedPackZipper;
import io.shardmc.arte.common.util.lambda.PackZipperCreator;

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
