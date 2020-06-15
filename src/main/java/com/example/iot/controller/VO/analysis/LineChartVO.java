package com.example.iot.controller.VO.analysis;

import com.example.iot.util.AggregationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineChartVO {
    /**
     * 横轴：时间
     */
    private List<LocalDateTime> timePoints;
    /**
     * 纵轴：度量值
     */
    private List<Metric> metrics;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Metric {
        private Integer deviceId;
        private Integer channelId;
        private Integer fieldId;
        private AggregationType aggregationType;
        private List<Double> values;

        public Metric(TimingAnalysisForm.MeasurePoint measurePoint, List<Double> values) {
            BeanUtils.copyProperties(measurePoint, this);
            this.values = values;
        }
    }
}
