package com.example.iot.service.ConnectionService.connection.MQTT;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubClient {
    @Autowired
    SubCallBack subCallBack;

    private static final String HOST = "tcp://101.37.80.37:1883";
    private static final String CLIENTID = "subClient";
    private static final String USERNAME = "test";
    private static final String PASSWORD = "test";
    private MqttClient mqttClient;

    public void connect() throws MqttException {
        mqttClient = new MqttClient(HOST, CLIENTID, new MemoryPersistence());

        SubTopicHandler.setMqttClient(mqttClient);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());
        options.setConnectionTimeout(1000);
        options.setKeepAliveInterval(1800);
        options.setAutomaticReconnect(true);

        options.setWill("close", "offline".getBytes(),0,true);

        mqttClient.setCallback(subCallBack);
        mqttClient.connect(options);
    }

    public void addSub(String[] topics, int qos) throws MqttException{
        SubTopicHandler.addSub(topics, qos);
    }

    public void addSub(String topic, int qos) throws MqttException{
        SubTopicHandler.addSub(topic, qos);
    }

    public void removeSub(String[] topics) throws MqttException {
        SubTopicHandler.removeSub(topics);
    }

    public void removeSub(String topics) throws MqttException {
        SubTopicHandler.removeSub(topics);
    }
}
