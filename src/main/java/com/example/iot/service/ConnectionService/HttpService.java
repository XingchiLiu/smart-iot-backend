package com.example.iot.service.ConnectionService;

import com.example.iot.controller.VO.MessageForm;
import com.example.iot.domain.ReceivedMessage;
import com.example.iot.service.MongoDeviceMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HttpService {
    @Autowired
    MongoDeviceMessageService mongoDeviceMessageService;

    public void sendMessageToDevice(MessageForm messageForm){
        RestTemplate restTemplate = new RestTemplate();
        // TODO get url of the device
        String url = "http://127.0.0.1:8000/test";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Object> test = new LinkedMultiValueMap<>();
        for(int i = 0; i < messageForm.getData().size(); i++){
            for(Map.Entry<String, Object> entry : messageForm.getData().get(i).entrySet()){
                test.add(entry.getKey(),entry.getValue());
            }
        }

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(test, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        System.out.println(response.getBody());

        ReceivedMessage receivedMessage = new ReceivedMessage();
        receivedMessage.setTopic(messageForm.getTopic());
        receivedMessage.setChannelId(messageForm.getChannelId());
        receivedMessage.setDate(messageForm.getDate());
        receivedMessage.setData(messageForm.getData());
        receivedMessage.setDeviceId(messageForm.getDeviceId());
        mongoDeviceMessageService.saveMessage(receivedMessage);
    }

}
