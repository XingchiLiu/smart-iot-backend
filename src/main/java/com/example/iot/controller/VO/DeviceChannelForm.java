package com.example.iot.controller.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class DeviceChannelForm {

    @ApiModelProperty(value = "id", required = false)
    private int id;

    // 数据通道的类型 0：向上 1：向下
    @ApiModelProperty(value = "数据通道的类型")
    private int channelType;

    // 此通道所属的设备id
    @ApiModelProperty(value = "此通道所属的设备id")
    private int deviceId;

    // 此通道所属的设备名称
    @ApiModelProperty(value = "此通道所属的设备名称")
    private String deviceName;

    //此通道所属的模板通道id，-1说明没有模板通道
    @ApiModelProperty(value = "此通道所属的模板通道id")
    private int templateChannelId;

    // 用来表示的通道名
    @ApiModelProperty(value = "通道名称")
    private String channelName;
}
