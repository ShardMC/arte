package the.grid.smp.arte.pack.manager.impl;

import the.grid.smp.arte.Arte;
import the.grid.smp.arte.pack.manager.PackManager;
import the.grid.smp.arte.util.lambda.PackZipperCreator;

public class AsyncPackManager extends PackManager {

    public AsyncPackManager(Arte arte) {
        super(arte);
    }

    @Override
    protected void reload(PackZipperCreator zipper) {
        new Thread(() -> super.reload(zipper)).start();
    }
}
