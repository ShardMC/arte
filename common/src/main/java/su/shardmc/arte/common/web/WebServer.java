package su.shardmc.arte.common.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import su.shardmc.arte.common.Arte;
import su.shardmc.arte.common.logger.ArteLogger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;

public class WebServer {

    private final ArteLogger logger;
    private final String address;

    private boolean enabled = false;
    private HttpServer server;

    public WebServer(ArteLogger logger, String address) {
        this.logger = logger;
        this.address = address;

        this.logger.info("Initialized web-server!");
    }

    public WebServer(Arte arte, String address) {
        this(arte.logger(), address);
    }

    public WebServer(Arte arte) {
        this(arte, arte.config().getAddress());
    }

    public void start(int port) {
        // Server is already enabled stop code
        if (this.enabled)
            return;

        try {
            this.server = HttpServer.create(new InetSocketAddress(port), 0);

            this.server.setExecutor(null);
            this.server.start();
            this.enabled = true;
        } catch (Exception e) {
            this.stop();
        }
    }

    public String getAddress(Hostable hostable) {
        String address = hostable.getName();

        if (!address.startsWith("/"))
            address = "/" + address;

        return "http://" + this.address + ":" + this.server.getAddress().getPort() + address;
    }

    public void host(Hostable hostable) throws IOException {
        hostable.host(this);
    }

    public void host(Hostable hostable, Path file) throws IOException {
        this.host(file, hostable.getName());
    }

    public void host(Path file, String path) throws IOException {
        if (!path.startsWith("/"))
            path = "/" + path;

        this.logger.info("Hosting", file.toString(), "at:", "'" + path + "'");
        this.server.createContext(path, new FileHandler(file));
    }

    public void stop() {
        this.server.stop(0);
        this.enabled = false;
    }

    public void restart(int port) {
        if (this.enabled) {
            this.stop();
        }

        this.start(port);
    }

    static class FileHandler implements HttpHandler {

        private final byte[] data;

        public FileHandler(byte[] data) {
            this.data = data;
        }

        public FileHandler(Path path) throws IOException {
            this(Files.readAllBytes(path));
        }

        @Override
        public void handle(HttpExchange t) throws IOException {
            t.sendResponseHeaders(200, this.data.length);

            try (OutputStream stream = t.getResponseBody()) {
                stream.write(this.data);
            }
        }
    }
}