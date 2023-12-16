package the.grid.smp.arte.util.lambda;

import java.io.IOException;

@FunctionalInterface
public interface RunnableIO {
    void run() throws IOException;
}
