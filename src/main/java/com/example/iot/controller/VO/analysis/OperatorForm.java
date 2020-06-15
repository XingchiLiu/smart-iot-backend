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
     * 算子名称
     */
    private String name;
    /**
     * 算子描述
     */
    private String description;
    /**
     * javascript代码
     */
    private String jsCode;
    /**
     * 函数名称
     */
    private String funcName;
}
