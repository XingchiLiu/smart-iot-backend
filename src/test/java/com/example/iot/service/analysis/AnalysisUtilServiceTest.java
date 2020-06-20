package com.example.iot.service.analysis;

import com.example.iot.controller.VO.analysis.DeviceDataVO;
import com.example.iot.domain.analysis.DeviceData;
import com.example.iot.repository.analysis.mapper.DeviceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AnalysisUtilServiceTest {
    @Autowired
    private AnalysisUtilService analysisUtilService;
    @Resource
    private DeviceMapper deviceMapper;

    @Test
    void getAllDevicesInfo() {
        List<DeviceDataVO> deviceDataVOs = analysisUtilService.getAllDevicesInfo();
        List<DeviceData> deviceDatas = deviceMapper.getAllDevicesInfo();
        assertNotNull(deviceDataVOs);
        assertNotNull(deviceDatas);
        assertTrue(deviceDatas.size() >= deviceDataVOs.size());
    }
}