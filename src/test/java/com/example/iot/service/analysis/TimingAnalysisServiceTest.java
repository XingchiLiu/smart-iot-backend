package com.example.iot.service.analysis;

import com.example.iot.controller.VO.analysis.LineChartVO;
import com.example.iot.controller.VO.analysis.TimingAnalysisForm;
import com.example.iot.controller.VO.analysis.TimingAnalysisForm.*;
import com.example.iot.util.AggregationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TimingAnalysisServiceTest {
    @Autowired
    private TimingAnalysisService timingAnalysisService;

    @Test
    void analysisByLineChart() {
        MeasurePoint measurePoint1 = new MeasurePoint(1, 1, 1, AggregationType.AVG);
        MeasurePoint measurePoint2 = new MeasurePoint(1, 1, 2, AggregationType.COUNT);
        TimingAnalysisForm form = new TimingAnalysisForm(LocalDateTime.of(2020,6,1,0,0),
                LocalDateTime.of(2020,6,20,0,0),
                (long) 1200, Arrays.asList(measurePoint1, measurePoint2));
        LineChartVO lineChartVO = timingAnalysisService.analysisByLineChart(form);
        assertNotNull(lineChartVO);
    }
}