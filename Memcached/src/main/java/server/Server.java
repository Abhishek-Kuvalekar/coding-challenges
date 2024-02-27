package server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Server {
    private final int port;
    private ServerSocket serverSocket;
    private List<ConnectionHandler> connectionHandlers;
    private ExecutorService executorService;

    public Server(int port) {
        this.port = port;
        this.connectionHandlers = new ArrayList<>();
        this.executorService = Executors.newCachedThreadPool();
    }

    public void start() {
        this.initializeServer();
        this.handleConnections();
    }

    private void initializeServer() {
        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException ex) {
            log.error("Failed to initialize memcached server", ex);
            throw new RuntimeException(ex);
        }

        log.info("Started listening on port: {}", this.port);
    }

    private void handleConnections() {
        Thread connectionHandlerThread = new Thread(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    ConnectionHandler connectionHandler = new ConnectionHandler(socket);
                    connectionHandlers.add(connectionHandler);
                    executorService.submit(connectionHandler);
                } catch (Exception ex) {
                    log.error("Failed to create a connection with a client", ex);
                }
            }
        });

        connectionHandlerThread.start();
    }

}
