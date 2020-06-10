package com.example.iot.service.ConnectionService.connection.connectionImpl;


import com.example.iot.service.ConnectionService.connection.ConnectionManagement;
import com.example.iot.service.ConnectionService.connection.MQTT.PubClient;
import com.example.iot.service.ConnectionService.connection.MQTT.SubClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MQTTConnectionImpl implements ConnectionManagement {
    PubClient pubClient;
    SubClient subClient;

    public MQTTConnectionImpl(){
        pubClient = new PubClient();
        subClient = new SubClient();
    }

    public void getConnection() {
        try {
            pubClient.connect();
            subClient.connect();
        }catch (MqttException e){
            e.printStackTrace();
        }
    }

    public boolean addSub(String[] topics, int qos){
        try {
            if(qos != 0 && qos != 1 && qos != 2){
                qos = 0;
            }
            subClient.addSub(topics, qos);
            return true;
        }catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addPub(String topic, int qos, String msg){
        try {
            if(qos != 0 && qos != 1 && qos != 2){
                qos = 0;
            }
            pubClient.addPub(topic, qos, msg);
            return true;
        }catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeSub(String[] topics){
        try {
            subClient.removeSub(topics);
            return true;
        }catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
    }
}
