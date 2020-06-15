package com.example.iot.controller;

import com.example.iot.IotApplicationTests;
import com.example.iot.controller.VO.MessageForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MessageControllerTest extends IotApplicationTests {
    @Autowired
    MessageController messageController;

    @Test
    void sendMessageToPlatform() {
    }

    @Test
    void sendMessageToDevice() {
        MessageForm messageForm = new MessageForm();
        Map<String, Object> map = new HashMap<>();
        map.put("test", 1);
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map);
        messageForm.setData(list);

        messageController.sendMessageToDevice(messageForm, 0);
    }
}