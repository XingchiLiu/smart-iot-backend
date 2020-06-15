package com.example.iot.service.ConnectionService;

import com.example.iot.controller.VO.MessageForm;
import com.example.iot.service.DeviceMessageAndDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class HttpService {
    @Autowired
    DeviceMessageAndDemandService deviceMessageAndDemandService;

    public void sendMessageToDevice(MessageForm messageForm) {
        RestTemplate restTemplate = new RestTemplate();
        // TODO get url of the device
        String url = "http://127.0.0.1:8000/test";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Object> test = new LinkedMultiValueMap<>();
        for (int i = 0; i < messageForm.getData().size(); i++) {
            for (Map.Entry<String, Object> entry : messageForm.getData().get(i).entrySet()) {
                test.add(entry.getKey(), entry.getValue());
            }
        }

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(test, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        System.out.println(response.getBody());

        deviceMessageAndDemandService.generateAndSaveDemandMessageFromMessageForm(messageForm);
    }

}
