package the.grid.smp.arte.pack.zipper.grouped;

import the.grid.smp.arte.pack.zipper.BasicPackZipper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

public abstract class GroupedPackZipper extends BasicPackZipper {

    public GroupedPackZipper(Logger logger, Path root, Path output) throws IOException {
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
