package com.example.iot.repository.analysis.repo;

import com.example.iot.domain.DeviceMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DeviceMessageRepoTest {
    @Autowired
    private DeviceMessageRepo deviceMessageRepo;

    @Test
    void getLastMsgByTopic() {
        DeviceMessage deviceMessage = deviceMessageRepo.getLastMsgByTopic("1");
        if (deviceMessage != null) {
            assertNotNull(deviceMessage);
            assertNotNull(deviceMessage.getDate());
            assertNotNull(deviceMessage.getTopic());
            assertNotNull(deviceMessage.getData());
        }
    }

    @Test
    void getAllMsgByTopic() {
        List<DeviceMessage> deviceMessages = deviceMessageRepo.getAllMsgByTopic("1");
        assertNotNull(deviceMessages);
    }

    @Test
    void getMsgByTopicAndTimeInterval() {
        List<DeviceMessage> deviceMessages = deviceMessageRepo.getMsgByTopicAndTimeInterval("1",
                LocalDateTime.of(2020, 1, 1, 0, 0),
                LocalDateTime.now());
        assertNotNull(deviceMessages);
    }
}