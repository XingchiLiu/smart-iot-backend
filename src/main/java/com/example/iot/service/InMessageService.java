package com.example.iot.service;

import com.example.iot.service.ConnectionService.MqttService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InMessageService {
    @Autowired
    MqttService mqttService;

    public boolean addSub(String[] topics, int qos) {
        return mqttService.addSub(topics, qos);
    }

    public boolean addSub(String topic, int qos) {
        return mqttService.addSub(topic, qos);
    }

    public boolean addPub(String topic, int qos, String msg) throws MqttException {
        return mqttService.addPub(topic, qos, msg);
    }

    public boolean removeSub(String[] topics) {
        return mqttService.removeSub(topics);
    }

    public boolean removeSub(String topic) {
        return mqttService.removeSub(topic);
    }
}
