package org.example;

import org.example.server.ProxyServer;

public class ForwardProxy {
    public static void main(String[] args) {
        ProxyServer proxyServer = new ProxyServer(9989);
        proxyServer.start();
    }
}
