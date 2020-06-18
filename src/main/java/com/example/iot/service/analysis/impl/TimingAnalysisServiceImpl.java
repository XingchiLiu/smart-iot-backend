package com.example.iot.service.analysis.impl;

import com.example.iot.controller.VO.analysis.TimingAnalysisForm;
import com.example.iot.controller.VO.analysis.TimingAnalysisForm.*;
import com.example.iot.controller.VO.analysis.LineChartVO;
import com.example.iot.controller.VO.analysis.LineChartVO.*;
import com.example.iot.domain.ChannelDataField;
import com.example.iot.domain.DeviceMessage;
import com.example.iot.repository.analysis.mapper.DeviceMapper;
import com.example.iot.repository.analysis.repo.DeviceMessageRepo;
import com.example.iot.service.analysis.TimingAnalysisService;
import com.example.iot.util.AggregationType;
import com.example.iot.util.FieldType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhm
 */
@Slf4j
@Service
public class TimingAnalysisServiceImpl implements TimingAnalysisService {
    @Resource
    private DeviceMapper deviceMapper;

    private DeviceMessageRepo deviceMessageRepo;

    @Autowired
    public TimingAnalysisServiceImpl(DeviceMessageRepo deviceMessageRepo) {
        this.deviceMessageRepo = deviceMessageRepo;
    }

    @Override
    public LineChartVO analysisByLineChart(TimingAnalysisForm form) {
        List<LocalDateTime> timePoints;
        List<Metric> metrics = new ArrayList<>(form.getMeasurePoints().size());
        try {
            LocalDateTime startTime = form.getStartTime();
            LocalDateTime endTime = form.getEndTime();
            Duration duration = Duration.between(startTime, endTime);
            timePoints = Stream
                    .iterate(startTime, t -> t.plusMinutes(form.getIntervalMinutes()))
                    .limit(duration.toMinutes() / form.getIntervalMinutes() + 1)
                    .filter(t -> t.isAfter(startTime) && t.isBefore(endTime))
                    .collect(Collectors.toList());
            for (MeasurePoint measurePoint : form.getMeasurePoints()) {
                Metric metric = analysisMeasurePoint(measurePoint,
                        form.getIntervalMinutes(), timePoints.size(), startTime, endTime);
                if (metric == null) {
                    metric = new Metric(measurePoint, Arrays.asList(new Double[timePoints.size()]));
                }
                metrics.add(metric);
            }
        } catch (Exception e) {
            log.error("Server Error: analysisByLineChart(" + form.toString() + ")");
            e.printStackTrace();
            return null;
        }
        return new LineChartVO(timePoints, metrics);
    }

    private Metric analysisMeasurePoint(MeasurePoint measurePoint, Long intervalMinutes, Integer size,
                                        LocalDateTime startTime, LocalDateTime endTime) {
        String topic = measurePoint.getChannelId().toString();
        // Step 1: 获取数据
        List<DeviceMessage> deviceMessages = deviceMessageRepo
                .getMsgByTopicAndTimeInterval(topic, startTime, endTime);
        // Step 2: 根据时间聚合
        ChannelDataField field = deviceMapper.getChannelDataFieldById(measurePoint.getFieldId());
        if (field == null || FieldType.valueOf(field.getFieldType()) != FieldType.DECIMAL) {
            return null;
        }
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Map<Long, List<DeviceMessage>> timedMsgs = deviceMessages.stream()
                .collect(Collectors.groupingBy(deviceMessage -> {
                    Duration duration = Duration.between(startTime,
                            LocalDateTime.ofInstant(deviceMessage.getDate().toInstant(), defaultZoneId));
                    return duration.toMinutes() / intervalMinutes;
                }));
        //Ste 3: 生成结果
        Double[] values = new Double[size];
        for (int i = 0; i < size; i++) {
            List<DeviceMessage> messages = timedMsgs.get((long) i);
            if (messages == null) {
                values[i] = null;
                continue;
            }
            List<Double> doubleValues = messages.stream()
                    .map(deviceMessage -> {
                        Map<String, Object> fieldMap = deviceMessage.getDataMap();
                        Object val =fieldMap.get(field.getFieldName());
                        if (val.getClass().equals(Integer.class)) {
                            return ((Integer) val).doubleValue();
                        } else if(val.getClass().equals(Double.class)){
                            return (Double) val;
                        } else {
                            return 0.0;
                        }
                    })
                    .collect(Collectors.toList());
            values[i] = aggregateMsg(doubleValues, measurePoint.getAggregationType());
        }
        return new Metric(measurePoint, Arrays.asList(values));
    }

    private Double aggregateMsg(List<Double> values, AggregationType type) {
        if (values == null || values.size() == 0) {
            return null;
        }
        Double result;
        switch (type) {
            case MAX:
                result = values.stream()
                        .max(Double::compareTo)
                        .orElse(null);
                break;
            case MIN:
                result = values.stream()
                        .min(Double::compareTo)
                        .orElse(null);
                break;
            case AVG:
                result = values.stream()
                        .mapToDouble(value -> value)
                        .average()
                        .orElse(0);
                break;
            case SUM:
                result = values.stream()
                        .mapToDouble(value -> value)
                        .sum();
                break;
            case COUNT:
                result = (double) values.size();
                break;
            default:
                return null;
        }
        return result;
    }
}