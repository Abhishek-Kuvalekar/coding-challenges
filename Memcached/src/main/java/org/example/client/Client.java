package org.example.client;

import lombok.extern.slf4j.Slf4j;
import org.example.cache.cmd.constants.CommandOutputMessage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Client {
    private int port;
    private String host;
    private int connectionCount;

    private static ExecutorService executorService;

    public Client(int port, String host, int connectionCount) {
        this.port = port;
        this.host = host;
        this.connectionCount = connectionCount;

        executorService = Executors.newCachedThreadPool();
    }

    public static void main(String[] args) {
        Client client = new Client(11211, "localhost", 100000);
        for (int i = 0; i < client.connectionCount; ++i) {
            executorService.submit(() -> client.createConnection());
        }
        executorService.shutdown();
        while (!executorService.isTerminated());
    }

    private void createConnection() {
        try (
            Socket socket = new Socket(host, port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            log.info("Thread-{} connected to the server", Thread.currentThread().getId());
            Random random = new Random();
            String key, line, message;
            int failureCount = 0, successCount = 0;
            int index;

            for (index = 0; index < 1; ++index) {

                key = String.valueOf(random.nextInt(5000));
                message = String.format("SET %s %s 0 %d%n", key, key, key.length());
                log.info("Thread-{} Sending message: {}", Thread.currentThread().getId(), message);

                writer.write(message);
                writer.flush();
                writer.write(String.format("%s%n", key));
                writer.flush();

                line = reader.readLine();
                if (CommandOutputMessage.ERROR.equals(line)) ++failureCount;
                else ++successCount;

                Thread.sleep(10);
            }

            log.info(
                    "Thread: {}, Total: {}, Successful: {}, Failed: {}",
                    Thread.currentThread().getId(),
                    index,
                    successCount,
                    failureCount
            );
        } catch (Exception ex) {
            log.info("Exception: {}", ex.getMessage());
        }
    }
}
