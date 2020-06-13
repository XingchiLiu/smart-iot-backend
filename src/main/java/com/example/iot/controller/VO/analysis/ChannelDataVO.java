package com.example.iot.controller.VO.analysis;

import com.example.iot.domain.analysis.ChannelData;
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
public class ChannelDataVO {
    private Integer channelId;

    private String channelName;

    private List<ChannelDataFieldVO> dataFields;

    public ChannelDataVO(ChannelData channelData) {
        this.channelId = channelData.getChannel().getId();
        this.channelName = channelData.getChannel().getChannelName();
        this.dataFields = channelData.getDataFields().stream()
                .map(ChannelDataFieldVO::new)
                .collect(Collectors.toList());
    }
}
