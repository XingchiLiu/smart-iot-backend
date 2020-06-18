package com.example.iot.service.ConnectionService.connectionCore;

import com.alibaba.fastjson.JSONObject;
import com.example.iot.service.DeviceMessageAndDemandService;
import com.example.iot.service.RuleService.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MessageReceiver {
    @Autowired
    DeviceMessageAndDemandService deviceMessageAndDemandService;
    @Autowired
    RuleService ruleService;

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


                deviceMessageAndDemandService.generateAndSaveMessageFromJson(message);
                ruleService.filterAndExecute(message);
            }

        }).start();

        return JSONObject.parseObject(response);
    }


}

