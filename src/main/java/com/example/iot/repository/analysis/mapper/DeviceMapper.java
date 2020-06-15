package com.example.iot.repository.analysis.mapper;

import com.example.iot.domain.ChannelDataField;
import com.example.iot.domain.analysis.DeviceData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 根据字段id获取数据通道字段
     * @param fieldId 字段id
     * @return 数据通道字段
     */
    ChannelDataField getChannelDataFieldById(@Param("fieldId") Integer fieldId);
}
