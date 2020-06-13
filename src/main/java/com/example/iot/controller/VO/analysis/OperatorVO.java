package com.example.iot.controller.VO.analysis;

import com.example.iot.domain.analysis.Operator;
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
public class OperatorVO {
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

    public OperatorVO(Operator operator) {
        BeanUtils.copyProperties(operator, this);
    }
}
