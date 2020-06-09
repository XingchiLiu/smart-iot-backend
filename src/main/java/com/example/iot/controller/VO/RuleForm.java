package com.example.iot.controller.VO;

import com.example.iot.util.RuleType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

/**
 * @author Karson
 * @CreateDate 2020/6/7 19:56
 */
@Data
@ApiModel
public class RuleForm {
    //设备id
    @ApiModelProperty(value = "应用规则的设备id")
    private int deviceId;
    //规则名
    @ApiModelProperty(value = "规则名")
    private String name;
    //规则描述，非必须。
    @ApiModelProperty(value = "规则描述", required = false)
    private String description;
    //规则类型
    @ApiModelProperty(value = "规则类型")
    private RuleType ruleType;
    //字段名
    @ApiModelProperty(value = "应用规则的字段名")
    private String fieldName;
    //阈值
    @ApiModelProperty(value = "规则阈值")
    private double thresholdVal;
}
