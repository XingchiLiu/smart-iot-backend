package com.example.iot.controller.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/25
 * @description:创建设备模板的格式
 */
@Data
@ApiModel
public class TemplateForm {

    @ApiModelProperty(value = "模板名，唯一")
    private String name;
    @ApiModelProperty(value = "连接方式，MQTT或者HTTP")
    private String connectionType;
    @ApiModelProperty(value = "描述", required = false)
    private String description;
}
