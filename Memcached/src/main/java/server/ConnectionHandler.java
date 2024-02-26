package server;

import lombok.extern.slf4j.Slf4j;

import java.net.Socket;

@Slf4j
public class ConnectionHandler implements Runnable {
    private final Socket socket;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
        log.info("Connection established with a client: {}", socket.getInetAddress());
    }

    @Override
    public void run() {
    }
}
