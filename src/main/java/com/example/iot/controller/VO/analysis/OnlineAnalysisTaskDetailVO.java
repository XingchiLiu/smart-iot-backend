package com.example.iot.controller.VO.analysis;

import com.example.iot.domain.DeviceChannel;
import com.example.iot.domain.analysis.OnlineAnalysisTaskDetail;
import com.example.iot.domain.analysis.OnlineAnalysisTaskDetail.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnlineAnalysisTaskDetailVO {
    /**
     * 任务id
     */
    private Integer taskId;
    /**
     * 任务名称
     */
    private String name;
    /**
     * 任务描述
     */
    private String description;
    /**
     * 模型id
     */
    private ModelVO model;
    /**
     * 输入源，即数据通道id列表
     */
    private List<DataChannel> channels;
    /**
     * 输入字段算子
     */
    private List<InputFuncVO> inputFields;

    /**
     * 数据通道
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataChannel {
        private Integer channelId;
        private String channelName;

        public DataChannel(DeviceChannel deviceChannel) {
            this.channelId = deviceChannel.getId();
            this.channelName = deviceChannel.getChannelName();
        }
    }

    /**
     * 数据通道字段
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChannelField {
        private Integer index;
        private Integer fieldId;
        private String fieldName;

        public ChannelField(FuncField funcField) {
            this.index = funcField.getIndex();
            this.fieldId = funcField.getFieldId();
            this.fieldName = funcField.getFieldName();
        }
    }

    /**
     * 模型输入字段对应的算子和参数
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InputFuncVO {
        /**
         * PMML模型输入字段id
         */
        private Integer inputFieldId;
        /**
         * PMML模型输入字段名称
         */
        private String inputFieldName;
        /**
         * 算子id
         */
        private OperatorVO operator;
        /**
         * 算子参数
         */
        private List<ChannelField> channelFields;

        public InputFuncVO(InputFunc inputFunc) {
            this.inputFieldId = inputFunc.getInputField().getFieldId();
            this.inputFieldName = inputFunc.getInputField().getFieldName();
            this.operator = new OperatorVO(inputFunc.getOperator());
            this.channelFields = inputFunc.getChannelDataFields().stream()
                    .map(ChannelField::new)
                    .sorted(Comparator.comparing(ChannelField::getIndex))
                    .collect(Collectors.toList());
        }
    }

    public OnlineAnalysisTaskDetailVO(OnlineAnalysisTaskDetail taskDetail) {
        this.taskId = taskDetail.getTaskId();
        this.name = taskDetail.getName();
        this.description = taskDetail.getDescription();
        this.model = new ModelVO(taskDetail.getModel());
        this.channels = taskDetail.getChannels().stream()
                .map(DataChannel::new)
                .collect(Collectors.toList());
        this.inputFields = taskDetail.getInputFuncs().stream()
                .map(InputFuncVO::new)
                .collect(Collectors.toList());
    }
}
