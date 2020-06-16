package com.example.iot.domain.analysis;

import com.example.iot.domain.Device;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceData {
    private Integer deviceId;

    /**
     * 设备
     */
    private Device device;

    /**
     * 设备的数据通道
     */
    private List<ChannelData> channels;


    public DeviceData(Integer deviceId) {
        this.deviceId = deviceId;
    }
}
