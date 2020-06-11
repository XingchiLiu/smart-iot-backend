package com.example.iot.controller.VO.analysis;

import com.example.iot.util.AggregationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimingAnalysisForm {
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 时间间隔，以分钟为单位
     */
    private Long interval;
    /**
     * 度量结点
     */
    private List<MeasurePoint> measurePoints;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MeasurePoint {
        private Integer deviceId;
        private Integer channelId;
        private Integer fieldId;
        private AggregationType aggregationType;

        public MeasurePoint(Integer deviceId, Integer channelId, Integer fieldId, Integer aggregationType) {
            this.deviceId = deviceId;
            this.channelId = channelId;
            this.fieldId = fieldId;
            this.aggregationType = AggregationType.valueOf(aggregationType);
        }

        public void setAggregationType(Integer aggregationType) {
            this.aggregationType = AggregationType.valueOf(aggregationType);
        }
    }
}
