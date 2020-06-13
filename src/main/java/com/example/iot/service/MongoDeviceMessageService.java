package com.example.iot.service;

import com.example.iot.domain.ReceivedMessage;
import com.example.iot.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoDeviceMessageService {
    @Autowired
    MessageRepository messageRepository;

    public void saveMessage(ReceivedMessage receivedMessage){
        messageRepository.save(receivedMessage);
    }
}
