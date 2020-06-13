package com.example.iot.controller.VO.analysis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelTaskForm {
    /**
     * 任务名称
     */
    private String name;
    /**
     * 任务描述
     */
    private String description;
    /**
     * 输入源，即数据通道id列表
     */
    private List<Integer> channelIds;
    /**
     * 模型id
     */
    private Integer modelId;
    /**
     * 输入字段算子
     */
    private List<Function> functions;


    /**
     * PMML模型输入字段的函数，每个输入字段对应一个函数
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Function {
        /**
         * PMML模型输入字段id
         */
        private Integer inputFieldId;
        /**
         * PMML模型输入字段名称
         */
        private String inputFieldName;
        /**
         * 数据通道下字段的id，按顺序传入javascript代码的函数中
         */
        private List<Integer> fieldId;
        /**
         * 算子id
         */
        private Integer operatorId;
    }
}
