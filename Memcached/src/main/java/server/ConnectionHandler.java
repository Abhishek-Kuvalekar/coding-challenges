package server;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()))
        ) {
            while (true) {
                String line = reader.readLine();
                if ("stop".equalsIgnoreCase(line)) break;
                log.info("Received input: {}", line);
                writer.write("yes, it works\n");
                writer.flush();
            }
        } catch (Exception ex) {
            log.error("Exception while reading from client: {}", this.socket.getInetAddress(), ex);
        }
    }
}
