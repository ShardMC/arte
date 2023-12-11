package the.grid.smp.arte.util;

import java.io.IOException;

@FunctionalInterface
public interface ConsumerIO<T> {
    void accept(T t) throws IOException;
}
