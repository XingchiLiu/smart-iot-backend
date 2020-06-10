package com.example.iot.service.ConnectionService.connection.MQTT;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class SubTopicHandler {
    private static MqttClient mqttClient = null;

    private static ArrayList<String> topics = new ArrayList<>();
    private static ArrayList<Integer> qoses = new ArrayList<>();

    public static void setMqttClient(MqttClient client){mqttClient = client;}

    public SubTopicHandler(MqttClient mqttClient){
        this.mqttClient = mqttClient;
        topics = new ArrayList<>();
        qoses = new ArrayList<>();
    }

    public static void addSub(String[] addedTopics, int addedQos) throws MqttException {
        int[] addedQoses = new int[addedTopics.length];
        Arrays.fill(addedQoses, addedQos);

        topics.addAll(Arrays.asList(addedTopics));
        for(int i = 0; i < addedQoses.length; i++){
            qoses.add(addedQoses[i]);
        }

        mqttClient.subscribe(addedTopics, addedQoses);
    }

    public static void removeSub(String[] removedTopics) throws MqttException {
        for(int i = 0; i < removedTopics.length; i++){
            int index = topics.indexOf(removedTopics[i]);
            if(index != -1){
                topics.remove(index);
                qoses.remove(index);
            }
        }
        mqttClient.unsubscribe(removedTopics);
    }

    public static void reSub() throws MqttException {
        int size = topics.size();
        String[] addedTopics = (String[]) topics.toArray(new String[size]);
        int[] addedQoses = new int[size];
        for(int i = 0; i < qoses.size(); i++){
            addedQoses[i] = qoses.get(i);
        }
        mqttClient.subscribe(addedTopics, addedQoses);
    }
}
