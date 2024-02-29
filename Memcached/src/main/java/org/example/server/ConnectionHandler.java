package org.example.server;

import lombok.extern.slf4j.Slf4j;
import org.example.cache.cmd.CmdParser;
import org.example.cache.cmd.CmdProcessor;
import org.example.cache.cmd.constants.CommandOutputMessage;
import org.example.cache.cmd.enums.CommandName;
import org.example.cache.cmd.model.CommandOutput;
import org.example.cache.cmd.model.MemcachedCommand;
import org.example.util.StringUtils;

import java.io.*;
import java.net.Socket;

@Slf4j
public class ConnectionHandler implements Runnable {
    private final Socket socket;
    private final CmdParser parser;
    private final CmdProcessor processor;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
        this.parser = CmdParser.getInstance();
        this.processor = CmdProcessor.getInstance();
        log.info("Connection established with a client: {}", socket.getInetAddress());
    }

    @Override
    public void run() {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()))
        ) {
            long byteCount = 0;
            String firstLine, secondLine;
            while (true) {
                firstLine = reader.readLine();
                if (StringUtils.isNullOrEmpty(firstLine)) continue;
                if (CommandName.STOP.name().equalsIgnoreCase(firstLine)) break;

                try {
                    MemcachedCommand command = parser.parse(firstLine);
                    byteCount = command.getData().getByteCount();
                    if (byteCount > 0) {
                        secondLine = reader.readLine();
                        if (StringUtils.isNullOrEmpty(secondLine) || secondLine.length() != byteCount) {
                            throw new IOException(
                                    String.format(
                                            "Value size mismatch. Expected: %d, Actual: %d",
                                            byteCount,
                                            secondLine.length()
                                    )
                            );
                        }
                        command.getData().setValue(secondLine);
                    }

                    CommandOutput output = processor.process(command);

                    writeMessage(writer, output);
                } catch (Exception ex) {
                    log.error("Error while parsing input from client: {}. Error: {}", socket.getInetAddress(), ex.getMessage());
                    writeMessage(
                            writer,
                            CommandOutput.builder().shortOutput(true).message(CommandOutputMessage.ERROR).build()
                    );
                }
            }
        } catch (Exception ex) {
            log.error("Exception while reading from client: {}", this.socket.getInetAddress(), ex);
        }
    }

    private void writeMessage(BufferedWriter writer, CommandOutput output) throws IOException {
        if (output.isShortOutput()) {
            writer.write(String.format("%s%n", output.getMessage()));
        } else {
            writer.write(
                    String.format(
                            "%s %s %d %d%n",
                            output.getMessage(),
                            output.getKey(),
                            output.getFlag(),
                            output.getByteCount()
                    )
            );
            writer.flush();
            writer.write(String.format("%s%n",output.getValue()));
        }
        writer.flush();
    }
}
