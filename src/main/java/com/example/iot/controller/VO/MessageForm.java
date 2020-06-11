package com.example.iot.controller.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@ApiModel
public class MessageForm {
    @ApiModelProperty(value = "mqtt的topic")
    private String topic;

    @ApiModelProperty(value = "具体消息")
    List<Map<String, Object>> messages = new ArrayList<>();
}
