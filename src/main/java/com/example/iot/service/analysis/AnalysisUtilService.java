package com.example.iot.service.analysis;

import com.example.iot.controller.VO.analysis.DeviceDataVO;

import java.util.List;

/**
 * @author zhm
 */
public interface AnalysisUtilService {
    /**
     * 获取所有设备及其传输的数据
     *
     * @return 设备信息列表，{@link DeviceDataVO}
     */
    List<DeviceDataVO> getAllDevicesInfo();
}
