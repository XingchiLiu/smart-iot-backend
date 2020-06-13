package com.example.iot.controller.VO.analysis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperatorForm {
    /**
     * 任务名称
     */
    private String name;
    /**
     * 任务描述
     */
    private String description;
    /**
     * javascript代码
     */
    private String jsCode;
}
