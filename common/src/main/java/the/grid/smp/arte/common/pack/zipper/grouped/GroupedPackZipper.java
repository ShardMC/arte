package the.grid.smp.arte.common.pack.zipper.grouped;

import the.grid.smp.arte.common.logger.ArteLogger;
import the.grid.smp.arte.common.pack.zipper.BasicPackZipper;

import java.io.IOException;
import java.nio.file.Path;

public abstract class GroupedPackZipper extends BasicPackZipper {

    public GroupedPackZipper(ArteLogger logger, Path root, Path output) throws IOException {
        super(logger, root, output);
    }

    @Override
    protected void zip(Context context) throws IOException {
        Path[][] groups = this.getGroups();

        for (Path[] group : groups) {
            context.addNamespace(group);
        }

        super.zip(context);
    }

    protected abstract Path[][] getGroups();
}
