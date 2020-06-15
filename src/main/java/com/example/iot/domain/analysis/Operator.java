package com.example.iot.domain.analysis;

import com.example.iot.controller.VO.analysis.OperatorForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @author zhm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Operator {
    /**
     * 算子id
     */
    private Integer operatorId;
    /**
     * 算子名称
     */
    private String name;
    /**
     * 算子描述
     */
    private String description;
    /**
     * 算子javascript代码
     */
    private String jsCode;
    /**
     * 函数名称
     */
    private String funcName;

    public Operator(OperatorForm operatorForm) {
        BeanUtils.copyProperties(operatorForm, this);
        if (description == null) {
            description = "";
        }
    }

    public Operator(Integer operatorId, OperatorForm operatorForm) {
        BeanUtils.copyProperties(operatorForm, this);
        this.operatorId = operatorId;
        if (description == null) {
            description = "";
        }
    }
}
