package com.example.iot.controller.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ChannelDataFieldForm {

    // 字段id
    @ApiModelProperty(value = "字段的id", required = false)
    private int id;

    // 此项数据的名称
    @ApiModelProperty(value = "数据名称")
    private String fieldName;

    // 此项数据的类型：0 boolean 1 int 2 string
    @ApiModelProperty(value = "数据的类型")
    private int fieldType;

    // 此项数据项对应的通道id
    @ApiModelProperty(value = "对应通道id")
    private int channelId;

    // 决定了channelId的类型：0 templateChannelId 1 deviceChannelId
    @ApiModelProperty(value = "channelId的类型")
    private int channelType;
}
