package com.example.iot.service.ConnectionService.connectionCore;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

public class MessageReceiver {
    private static ArrayList<JSONObject> receivedMessages = new ArrayList<>();
//    private static ArrayList<String> receivedRawMessages = new ArrayList<>();

    public static JSONObject returnResponse(String topic, String response) {
        System.out.println("Core: response received!");

        new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                JSONObject message = JSONObject.parseObject(response);
                System.out.println("Received Message: " + message);
                receivedMessages.add(message);
                System.out.println("Received Message Size: " + receivedMessages.size());
            }

        }).start();

        return JSONObject.parseObject(response);
    }
}

