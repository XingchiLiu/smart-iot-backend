package com.example.iot.service.ConnectionService.connection.MQTT;

import com.example.iot.service.ConnectionService.connectionCore.MessageReceiver;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SubCallBack implements MqttCallbackExtended {

    public void connectComplete(boolean reconnect, String s) {
        if(reconnect){
            System.out.println("Sub: Try Reconnect");
            try {
                SubTopicHandler.reSub();
                System.out.println("Sub: Reconnect Ready!");
                return;
            } catch (MqttException e) {
                System.out.println("Sub: Reconnect failed!");
                e.printStackTrace();
                return;
            }
        }
        System.out.println("Sub: Connection Ready!");
    }

    public void connectionLost(Throwable throwable) {
        System.out.println("Sub: Connection severed, may reconnect");
    }

    public void messageArrived(String s, MqttMessage mqttMessage){
        System.out.println("Sub: Get message topic: " + s);
        System.out.println("Sub: Get message content:" + mqttMessage);
        MessageReceiver.returnResponse(s,mqttMessage.toString());
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }


}
