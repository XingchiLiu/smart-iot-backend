package com.example.iot.repository.analysis.mapper;

import com.example.iot.domain.ChannelDataField;
import com.example.iot.domain.analysis.DeviceData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DeviceMapperTest {
    @Resource
    private DeviceMapper deviceMapper;

    @Test
    void getAllDevicesInfo() {
        List<DeviceData> deviceDatas = deviceMapper.getAllDevicesInfo();
        assertNotNull(deviceDatas);
        if (deviceDatas.size() != 0) {
            DeviceData deviceData = deviceDatas.get(0);
            assertNotNull(deviceData.getDeviceId());
            assertNotNull(deviceData.getDevice());
            assertNotNull(deviceData.getChannels());
        }
    }

    @Test
    void getChannelDataFieldById() {
        ChannelDataField channelDataField = deviceMapper.getChannelDataFieldById(1);
        if (channelDataField != null) {
            assertNotNull(channelDataField);
        }
    }
}