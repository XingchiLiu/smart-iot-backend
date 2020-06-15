package com.example.iot.service.analysis.impl;

import com.example.iot.controller.VO.analysis.DeviceDataVO;
import com.example.iot.domain.analysis.DeviceData;
import com.example.iot.repository.analysis.mapper.DeviceMapper;
import com.example.iot.service.analysis.AnalysisUtilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhm
 */
@Slf4j
@Service
public class AnalysisUtilServiceImpl implements AnalysisUtilService {
    @Resource
    private DeviceMapper deviceMapper;

    @Override
    public List<DeviceDataVO> getAllDevicesInfo() {
        try {
            List<DeviceData> deviceDatas = deviceMapper.getAllDevicesInfo();
            if (deviceDatas == null) {
                return null;
            }
            return deviceDatas.stream()
                    .map(DeviceDataVO::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("ERROR: getAllDevicesInfo()");
            e.printStackTrace();
            return null;
        }
    }
}
