package com.example.iot.domain.analysis;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelField {
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
}
