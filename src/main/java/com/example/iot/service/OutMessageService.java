package com.example.iot.service;

import com.alibaba.fastjson.JSON;
import com.example.iot.controller.VO.MessageForm;
import com.example.iot.service.ConnectionService.HttpService;
import com.example.iot.service.ConnectionService.MqttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutMessageService {
    @Autowired
    DeviceService deviceService;
    @Autowired
    MqttService mqttService;
    @Autowired
    HttpService httpService;

    public void sendMessageToDevice(int deviceId,MessageForm messageForm){
        String connectionType = deviceService.getDeviceById(deviceId).getConnectionType();
        if(connectionType.equals("HTTP")){
            httpService.sendMessageToDevice(messageForm);
        }
        else if(connectionType.equals("MQTT")){
            String message = JSON.toJSONString(messageForm);
            mqttService.addPub(messageForm.getTopic(), 0, message);
        }
    }
}
