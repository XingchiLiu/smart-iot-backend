package com.example.iot.controller.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class TemplateChannelForm {
    @ApiModelProperty(value = "id", required = false)
    private int id;

    // 数据通道的类型 0：向上 1：向下
    @ApiModelProperty(value = "数据通道的类型")
    private int channelType;

    // 此通道所属的设备模板id
    @ApiModelProperty(value = "此通道所属的设备模板id")
    private int templateId;

    // 同来标识的通道名
    @ApiModelProperty(value = "通道名称")
    private String channelName;
}
