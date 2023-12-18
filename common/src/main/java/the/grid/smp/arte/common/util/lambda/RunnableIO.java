package the.grid.smp.arte.common.util.lambda;

import java.io.IOException;

@FunctionalInterface
public interface RunnableIO {
    void run() throws IOException;
}
