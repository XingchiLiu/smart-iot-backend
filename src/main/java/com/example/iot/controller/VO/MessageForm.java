package com.example.iot.controller.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.*;

@Data
@ApiModel
public class MessageForm {
    @ApiModelProperty(value = "mqtt的topic，就是数据通道id")
    private String topic;

    @ApiModelProperty(value = "设备的id")
    private int deviceId;

    @ApiModelProperty(value = "通道的id")
    private int channelId;

    @ApiModelProperty(value = "发送的时间")
    private Date date;

    @ApiModelProperty(value = "要发送的数据")
    private List<Map<String,Object>> data;

}
