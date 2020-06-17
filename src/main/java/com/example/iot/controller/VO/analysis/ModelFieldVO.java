package com.example.iot.controller.VO.analysis;

import com.example.iot.domain.analysis.ModelField;
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
public class ModelFieldVO {
    /**
     * 字段id
     */
    private Integer fieldId;
    /**
     * 模型id
     */
    private Integer modelId;
    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 字段类型
     */
    private String dataType;
    /**
     * 字段操作类型
     */
    private String opType;

    public ModelFieldVO(ModelField modelField) {
        BeanUtils.copyProperties(modelField, this);
    }
}
