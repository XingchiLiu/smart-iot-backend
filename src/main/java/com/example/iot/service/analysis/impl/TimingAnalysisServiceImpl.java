package com.example.iot.service.analysis.impl;

import com.example.iot.controller.VO.analysis.LineChartVO;
import com.example.iot.controller.VO.analysis.TimingAnalysisForm;
import com.example.iot.service.analysis.TimingAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhm
 */
@Slf4j
@Service
public class TimingAnalysisServiceImpl implements TimingAnalysisService {
    @Override
    public LineChartVO analysisByLineChart(TimingAnalysisForm form) {
        //TODO：待MongoDB设备数据格式明确后完成
        return null;
    }
}
