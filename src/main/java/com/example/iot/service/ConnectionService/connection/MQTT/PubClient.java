package com.example.iot.service.ConnectionService.connection.MQTT;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PubClient{
    @Autowired
    PubCallback pubCallback;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Value("${mqtt.url}")
    private String url;

    @Value("${mqtt.sender.clientId}")
    private String clientId;

    private MqttClient mqttClient;

    public void connect() throws MqttException {
        mqttClient = new MqttClient(url, clientId, new MemoryPersistence());

        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setConnectionTimeout(1000);
        options.setKeepAliveInterval(1800);
        options.setAutomaticReconnect(true);

        options.setWill("close", "offline".getBytes(),0,true);

        mqttClient.setCallback(pubCallback);
        mqttClient.connect(options);
    }

    public void addPub(String topic, int qos, String msg)throws MqttException{
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(false);

        mqttMessage.setPayload(msg.getBytes());
        MqttTopic mqttTopic = mqttClient.getTopic(topic);
        publish(mqttTopic,mqttMessage);
    }

    public void publish(MqttTopic topic, MqttMessage message)throws MqttException{
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
    }
}
