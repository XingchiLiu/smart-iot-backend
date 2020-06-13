package com.example.iot.controller.VO.analysis;

import com.example.iot.domain.analysis.Model;
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
public class ModelVO {
    /**
     * 模型id
     */
    private Integer modelId;
    /**
     * 用户定义的模型名称
     */
    private String name;
    /**
     * 模型描述
     */
    private String description;

    public ModelVO(Model model) {
        BeanUtils.copyProperties(model, this);
    }
}
