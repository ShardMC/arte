package the.grid.smp.arte.pack.zipper.grouped;

import the.grid.smp.arte.Arte;

import java.io.IOException;
import java.nio.file.Path;

public class ManualGroupedPackZipper extends GroupedPackZipper {

    private final Arte arte;

    public ManualGroupedPackZipper(Arte arte, Path root, Path output) throws IOException {
        super(arte.getLogger(), root, output);

        this.arte = arte;
    }

    @Override
    protected Path[][] getGroups() {
        String[][] groups = this.arte.config().getGroups();
        Path[][] paths = new Path[groups.length][];

        for (int i = 0; i < groups.length; i++) {
            String[] group = groups[i];

            for (int n = 0; n < group.length; n++) {
                paths[i][n] = this.assets.resolve(group[n]);
            }
        }

        return paths;
    }
}
