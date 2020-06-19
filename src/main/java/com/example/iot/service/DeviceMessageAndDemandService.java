package com.example.iot.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.iot.controller.VO.MessageForm;
import com.example.iot.domain.DeviceDemand;
import com.example.iot.domain.DeviceMessage;
import com.example.iot.repository.DeviceDemandRepository;
import com.example.iot.repository.DeviceMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class DeviceMessageAndDemandService {
    @Autowired
    DeviceMessageRepository deviceMessageRepository;
    @Autowired
    DeviceDemandRepository deviceDemandRepository;

    public void generateAndSaveMessageFromJson(JSONObject message) {
        int direction = message.getIntValue("direction");
        if (direction == 0) {
            generateAndSaveDeviceMessageFromJson(message);
        } else if (direction == 1) {
            generateAndSaveDemandMessageFromJson(message);
        }
    }

    private void generateAndSaveDeviceMessageFromJson(JSONObject message) {
        DeviceMessage deviceMessage = new DeviceMessage();
        deviceMessage.setTopic(message.getString("topic"));
        deviceMessage.setDeviceId(message.getIntValue("deviceId"));
        deviceMessage.setChannelId(message.getIntValue("channelId"));
        List<Map<String,Object>> data = (List<Map<String, Object>>) JSONArray.parse(message.getJSONArray("data").toJSONString());
        for(int i = 0; i < data.size(); i++){
            for(Map.Entry<String,Object> entry:data.get(i).entrySet()){
                if(entry.getValue().getClass().toString().equals("class java.math.BigDecimal")) {
                    Double value_D = new Double(((BigDecimal) entry.getValue()).doubleValue());
                    entry.setValue(value_D);
                }
            }
        }
        deviceMessage.setData(data);
        deviceMessage.setDate(message.getDate("date"));
        saveMessage(deviceMessage);
    }

    private void generateAndSaveDemandMessageFromJson(JSONObject message) {
        DeviceDemand deviceDemand = new DeviceDemand();
        deviceDemand.setTopic(message.getString("topic"));
        deviceDemand.setDeviceId(message.getIntValue("deviceId"));
        deviceDemand.setChannelId(message.getIntValue("channelId"));
        List<Map<String,Object>> data = (List<Map<String, Object>>) JSONArray.parse(message.getJSONArray("data").toJSONString());
        for(int i = 0; i < data.size(); i++){
            for(Map.Entry<String,Object> entry:data.get(i).entrySet()){
                if(entry.getValue().getClass().toString().equals("class java.math.BigDecimal")) {
                    Double value_D = new Double(((BigDecimal) entry.getValue()).doubleValue());
                    entry.setValue(value_D);
                }
            }
        }
        deviceDemand.setData(data);        deviceDemand.setDate(message.getDate("date"));
        saveDemand(deviceDemand);
    }


    public void generateAndSaveDemandMessageFromMessageForm(MessageForm messageForm) {
        DeviceDemand deviceDemand = new DeviceDemand();
        deviceDemand.setTopic(messageForm.getTopic());
        deviceDemand.setChannelId(messageForm.getChannelId());
        deviceDemand.setDate(messageForm.getDate());
        deviceDemand.setData(messageForm.getData());
        deviceDemand.setDeviceId(messageForm.getDeviceId());
        saveDemand(deviceDemand);
    }

    private void saveMessage(DeviceMessage deviceMessage) {
        deviceMessageRepository.save(deviceMessage);
    }

    private void saveDemand(DeviceDemand deviceDemand) {
        deviceDemandRepository.save(deviceDemand);
    }
}
