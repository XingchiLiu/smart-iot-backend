package com.example.iot.service.ConnectionService.connectionCore;

import com.alibaba.fastjson.JSONObject;
import com.example.iot.domain.ReceivedMessage;
import com.example.iot.service.MongoDeviceMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MessageReceiver {
    @Autowired
    MongoDeviceMessageService mongoDeviceMessageService;

    private ArrayList<JSONObject> receivedMessages = new ArrayList<>();
//    private static ArrayList<String> receivedRawMessages = new ArrayList<>();

    public JSONObject returnResponse(String topic, String response) {
        System.out.println("Core: response received!");

        new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                JSONObject message = JSONObject.parseObject(response);
                System.out.println("Received Message: " + message);
                receivedMessages.add(message);
                System.out.println("Received Message Size: " + receivedMessages.size());


                ReceivedMessage receivedMessage = new ReceivedMessage(message);
                mongoDeviceMessageService.saveMessage(receivedMessage);
            }

        }).start();

        return JSONObject.parseObject(response);
    }


}

