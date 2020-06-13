package com.example.iot.domain.analysis;

import com.example.iot.domain.ChannelDataField;
import com.example.iot.domain.DeviceChannel;
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
public class ChannelData {
    private Integer channelId;

    /**
     * 数据通道
     */
    private DeviceChannel channel;

    /**
     * 通道字段
     */
    private List<ChannelDataField> dataFields;


    public ChannelData(Integer channelId) {
        this.channelId = channelId;
    }
}
