package the.grid.smp.arte.web;

import java.io.IOException;

public interface Hostable {
    void host(WebServer server) throws IOException;
    String getName();
}
