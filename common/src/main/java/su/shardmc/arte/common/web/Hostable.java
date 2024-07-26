package su.shardmc.arte.common.web;

import java.io.IOException;

public interface Hostable {
    void host(WebServer server) throws IOException;
    String getName();

    default String getAddress(WebServer server) {
        return server.getAddress(this);
    }
}
