package com.example.iot.controller.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/25
 * @description:创建设备的格式
 */
@Data
@ApiModel
public class DeviceForm {

    @ApiModelProperty(value = "设备名，模板内唯一", required = false)
    private String name;
    @ApiModelProperty(value = "模板id")
    private int templateId;
    @ApiModelProperty(value = "描述", required = false)
    private String description;
}
