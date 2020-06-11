package com.example.iot.repository.analysis;

import com.example.iot.domain.DeviceData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhm
 */
@Mapper
public interface DeviceMapper {
    /**
     * 获取所有设备及其传输的数据
     *
     * @return 设备信息列表，{@link DeviceData}
     */
    List<DeviceData> getAllDevicesInfo();
}
