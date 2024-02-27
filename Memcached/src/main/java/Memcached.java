import cmd.model.ParsedArguments;
import cmd.parser.ArgumentParser;
import lombok.extern.slf4j.Slf4j;
import server.Server;

@Slf4j
public class Memcached {
    public static void main(String[] args) {
        ParsedArguments arguments = parseArguments(args);
        Server server = new Server(arguments.getPort().getValue());
        server.start();
    }

    private static ParsedArguments parseArguments(String[] args) {
        try {
            ArgumentParser argumentParser = new ArgumentParser();
            return argumentParser.parseArguments(args);
        } catch (Exception ex) {
            log.error("Error while parsing arguments: {}", ex.getMessage());
            System.exit(1);
        }
        return null;
    }
}
