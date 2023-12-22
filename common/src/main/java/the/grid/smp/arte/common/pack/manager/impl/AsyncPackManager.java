package the.grid.smp.arte.common.pack.manager.impl;

import the.grid.smp.arte.common.Arte;
import the.grid.smp.arte.common.pack.manager.PackManager;
import the.grid.smp.arte.common.util.lambda.PackZipperCreator;

public class AsyncPackManager extends PackManager {

    public AsyncPackManager(Arte arte) {
        super(arte);
    }

    @Override
    protected void reload(PackZipperCreator zipper) {
        //new Thread(() ->
                super.reload(zipper);
        //).start();
    }

    @Override
    public void stop() {
        new Thread(super::stop).start();
    }
}
