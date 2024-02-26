import server.Server;

public class Memcached {
    public static void main(String[] args) {
        Server server = new Server(11211);
        server.start();
    }
}
