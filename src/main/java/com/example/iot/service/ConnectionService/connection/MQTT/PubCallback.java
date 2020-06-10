package com.example.iot.service.ConnectionService.connection.MQTT;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PubCallback implements MqttCallbackExtended {
    public void connectComplete(boolean b, String s) {
        System.out.println("Pub: Connection Ready!");
    }

    public void connectionLost(Throwable throwable) {
        System.out.println("Pub: Connection severed, may reconnect");
    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println("Pub: Get message topic: " + s);
        System.out.println("Pub: Get message content:" + mqttMessage);
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("Pub: Publish complete");
    }
}
