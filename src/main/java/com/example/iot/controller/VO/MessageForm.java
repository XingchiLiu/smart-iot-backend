package com.example.iot.controller.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@ApiModel
public class MessageForm {
    @ApiModelProperty(value = "mqtt的topic，就是数据通道id")
    private String topic;

    @ApiModelProperty(value = "设备的id")
    private int deviceId;

    @ApiModelProperty(value = "设备通道的id")
    private int channelId;

    @ApiModelProperty(value = "发送的时间")
    private Date date;

    @ApiModelProperty(value = "数据的发送方向")
    // 如果是 0，为设备 -->平台，如果是 1，为平台 --> 设备
    private int direction;

    @ApiModelProperty(value = "要发送的数据")
    private List<Map<String, Object>> data;

}
