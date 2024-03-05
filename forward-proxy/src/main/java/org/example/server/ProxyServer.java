package org.example.server;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.example.common.Constants;
import org.example.util.NumberUtils;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.MDC;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class ProxyServer {
    private final int port;
    private final ExecutorService serverExecutor;
    private final ExecutorService connectionExecutor;

    public ProxyServer(int port) {
        this.port = port;
        this.serverExecutor = Executors.newSingleThreadExecutor();
        this.connectionExecutor = Executors.newCachedThreadPool();
    }

    public void start() {
        //serverExecutor.submit(() -> {
                    try (ServerSocket serverSocket = new ServerSocket(port)) {
                        log.info("Proxy server started at port {}", port);
                        acceptConnections(serverSocket);
                    } catch (Exception ex) {
                        log.error("Error while starting proxy server: {}", ex.getMessage());
                    }
        //});
    }

    private void acceptConnections(ServerSocket serverSocket) {
        while (true) {
            try (Socket socket = serverSocket.accept()) {
                int id = NumberUtils.getRandomInteger();
                connectionExecutor.submit(new ConnectionHandler(id, socket));
            } catch (Exception ex) {
                log.error("Error while establishing a connection: {}", ex.getMessage());
            }
        }
    }
}
