package com.example.iot.service.ConnectionService.connection.MQTT;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.ArrayList;
import java.util.Arrays;


public class SubTopicHandler {
    private static MqttClient mqttClient = null;

    private static ArrayList<String> topics = new ArrayList<>();
    private static ArrayList<Integer> qoses = new ArrayList<>();

    public SubTopicHandler(MqttClient mqttClient) {
        SubTopicHandler.mqttClient = mqttClient;
        topics = new ArrayList<>();
        qoses = new ArrayList<>();
    }

    public static void setMqttClient(MqttClient client) {
        mqttClient = client;
    }

    public static void addSub(String[] addedTopics, int addedQos) throws MqttException {
        int[] addedQoses = new int[addedTopics.length];
        Arrays.fill(addedQoses, addedQos);

        for (int i = 0; i < addedTopics.length; i++) {
            if (!topics.contains(addedTopics[i])) {
                topics.add(addedTopics[i]);
                qoses.add(addedQos);
            }
        }

        mqttClient.subscribe(addedTopics, addedQoses);
    }

    public static void addSub(String addedTopic, int addedQos) throws MqttException {
        if (!topics.contains(addedTopic)) {
            topics.add(addedTopic);
            qoses.add(addedQos);
        }
        mqttClient.subscribe(addedTopic, addedQos);
    }

    public static void removeSub(String[] removedTopics) throws MqttException {
        for (int i = 0; i < removedTopics.length; i++) {
            int index = topics.indexOf(removedTopics[i]);
            if (index != -1) {
                topics.remove(index);
                qoses.remove(index);
            }
        }
        mqttClient.unsubscribe(removedTopics);
    }

    public static void removeSub(String removedTopic) throws MqttException {
        int index = topics.indexOf(removedTopic);
        if (index != -1) {
            topics.remove(removedTopic);
            qoses.remove(index);
        }
        mqttClient.unsubscribe(removedTopic);
    }

    public static void reSub() throws MqttException {
        int size = topics.size();
        String[] addedTopics = topics.toArray(new String[size]);
        int[] addedQoses = new int[size];
        for (int i = 0; i < qoses.size(); i++) {
            addedQoses[i] = qoses.get(i);
        }
        mqttClient.subscribe(addedTopics, addedQoses);
    }
}
