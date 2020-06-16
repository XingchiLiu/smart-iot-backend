package com.example.iot.domain.analysis;

import com.example.iot.domain.DeviceChannel;
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
public class OnlineAnalysisTaskDetail {
    private Integer taskId;
    private String name;
    private String description;
    private Model model;
    private List<DeviceChannel> channels;
    private List<InputFunc> inputFuncs;

    /**
     * 模型输入字段对应的算子和参数
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InputFunc {
        private Integer inputFieldId;

        private ModelField inputField;
        private Operator operator;
        private List<FuncField> channelDataFields;

        public InputFunc(Integer inputFieldId) {
            this.inputFieldId = inputFieldId;
        }
    }

    /**
     * 模型输入字段对应的算子和参数
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FuncField {
        private Integer index;
        private Integer fieldId;
        private String fieldName;
        private Integer channelId;
    }
}
