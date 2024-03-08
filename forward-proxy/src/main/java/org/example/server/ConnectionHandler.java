package org.example.server;

import lombok.extern.slf4j.Slf4j;
import org.example.common.Constants;
import org.example.util.NumberUtils;
import org.example.util.StringUtils;
import org.slf4j.MDC;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Objects;

@Slf4j
public class ConnectionHandler implements Runnable {
    private int id;
    private final Socket socket;

    public ConnectionHandler(Socket socket) {
        this.id = Math.abs(NumberUtils.getRandomInteger());
        this.socket = socket;
    }

    @Override
    public void run() {
        MDC.put(Constants.MDC_KEY_CONNECTION_ID, String.valueOf(this.id));

        log.info("Connection established with the client: {}", socket.getInetAddress());
        handleRequests();
    }

    private void handleRequests() {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()))
        ) {
            while (true) {
                String line = reader.readLine();

                if (StringUtils.isNull(line)) {
                    closeConnection();
                    break;
                }

                if (StringUtils.isEmpty(line)) {
                    continue;
                }

                log.info("Received Message: {}", line);

            }
        } catch (Exception ex) {
            log.error("Error while accepting messages: {}", ex.getMessage());
        }
    }

    private void closeConnection() {
        log.info("Closing connection with the client: {}", socket.getInetAddress());

        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (Exception ex) {
            log.error("Error while disconnecting client: {}. {}", socket.getInetAddress(), ex.getMessage());
        }

        log.info("Closed connection with the client: {}", socket.getInetAddress());
        MDC.clear();
    }
}
