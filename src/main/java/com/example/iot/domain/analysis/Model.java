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
public class Model {
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
    /**
     * 模型文件名
     */
    private String filename;
}
