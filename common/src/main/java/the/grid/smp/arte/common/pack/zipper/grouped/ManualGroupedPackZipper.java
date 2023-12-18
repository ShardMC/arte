package the.grid.smp.arte.common.pack.zipper.grouped;

import the.grid.smp.arte.common.Arte;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ManualGroupedPackZipper extends GroupedPackZipper {

    private final Arte arte;

    public ManualGroupedPackZipper(Arte arte, Path root, Path output) throws IOException {
        super(arte.getLogger(), root, output);

        this.arte = arte;
    }

    @Override
    protected Path[][] getGroups() {
        List<List<String>> groups = this.arte.config().getGroups();
        Path[][] paths = new Path[groups.size()][];

        for (int i = 0; i < groups.size(); i++) {
            List<String> group = groups.get(i);

            for (int n = 0; n < group.size(); n++) {
                paths[i][n] = this.assets.resolve(group.get(n));
            }
        }

        return paths;
    }
}
