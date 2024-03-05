package org.example.server;

import lombok.extern.slf4j.Slf4j;
import org.example.common.Constants;
import org.example.util.StringUtils;
import org.slf4j.MDC;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

@Slf4j
public class ConnectionHandler implements Runnable {
    private final int id;
    private final Socket socket;

    public ConnectionHandler(int id, Socket socket) {
        this.id = id;
        this.socket = socket;
//        MDC.put(
//                Constants.MDC_KEY_CONNECTION_ID,
//                String.format(
//                        "%s: %d",
//                        Constants.MDC_VALUE_CONNECTION_ID,
//                        id
//                )
//        );
    }

    @Override
    public void run() {
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
        //MDC.clear();
    }
}
