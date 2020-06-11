package com.example.iot.service.ConnectionService.connection.connectionImpl;


import com.example.iot.service.ConnectionService.connection.ConnectionManagement;
import com.example.iot.service.ConnectionService.connection.HTTP.HttpClient;
import com.example.iot.service.ConnectionService.connection.HTTP.HttpServer;

public class HTTPConnectionService implements ConnectionManagement {
    HttpServer httpServer;

    public HTTPConnectionService(){
        httpServer = new HttpServer();
    }

    public void getConnection() {
        httpServer.connect();
//        HttpClient.doGet("localhost",8080,"index.html");
    }
}
