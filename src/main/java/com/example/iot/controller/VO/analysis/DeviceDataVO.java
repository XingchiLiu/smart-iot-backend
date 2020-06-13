package com.example.iot.controller.VO.analysis;

import com.example.iot.domain.analysis.DeviceData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDataVO {
    private Integer deviceId;

    private String deviceName;

    private List<ChannelDataVO> channels;

    public DeviceDataVO(DeviceData deviceData) {
        this.deviceId = deviceData.getDevice().getId();
        this.deviceName = deviceData.getDevice().getName();
        this.channels = deviceData.getChannels().stream()
                .map(ChannelDataVO::new)
                .collect(Collectors.toList());
    }
}
