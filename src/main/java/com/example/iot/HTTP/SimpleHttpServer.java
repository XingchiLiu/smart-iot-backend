package com.example.iot.HTTP;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/*
 * a simple static http server
 * for test use
 */
@Component
public class SimpleHttpServer implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpServer server = null;
                try {
                    server = HttpServer.create(new InetSocketAddress(8000), 0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                server.createContext("/test", new MyHandler());
                server.setExecutor(null); // creates a default executor
                server.start();
                System.out.println("HTTP Server Ready!");
            }
        }).start();
    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            byte[] response = "Welcome Real's HowTo test page".getBytes();
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();
        }
    }
}
