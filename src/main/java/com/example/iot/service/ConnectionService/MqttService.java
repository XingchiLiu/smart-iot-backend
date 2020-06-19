package com.example.iot.service.ConnectionService;


import com.example.iot.service.ChannelService;
import com.example.iot.service.ConnectionService.connection.MQTT.PubClient;
import com.example.iot.service.ConnectionService.connection.MQTT.SubClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class MqttService implements ApplicationRunner {
    @Autowired
    private PubClient pubClient;
    @Autowired
    private SubClient subClient;
    @Autowired
    ChannelService channelService;

    public void getConnection() {
        try {
            pubClient.connect();
            subClient.connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public boolean addSub(String[] topics, int qos) {
        try {
            if (qos != 0 && qos != 1 && qos != 2) {
                qos = 0;
            }
            subClient.addSub(topics, qos);
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addSub(String topic, int qos) {
        try {
            if (qos != 0 && qos != 1 && qos != 2) {
                qos = 0;
            }
            subClient.addSub(topic, qos);
            System.out.println("Sub: Topic " + topic + " Added!");
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addPub(String topic, int qos, String msg) throws MqttException{
        try {
            if (qos != 0 && qos != 1 && qos != 2) {
                qos = 0;
            }
            pubClient.addPub(topic, qos, msg);
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean removeSub(String[] topics) {
        try {
            subClient.removeSub(topics);
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeSub(String topic) {
        try {
            subClient.removeSub(topic);
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addInitialSub(){
        addSub(channelService.getInitialTopic(),0);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.getConnection();
        this.addInitialSub();
    }
}
