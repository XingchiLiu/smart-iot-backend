package com.example.iot.service.analysis;

import com.example.iot.controller.VO.analysis.LineChartVO;
import com.example.iot.controller.VO.analysis.TimingAnalysisForm;

/**
 * @author zhm
 */
public interface TimingAnalysisService {
    /**
     * 使用折线图显示根据时间段聚合后的设备信息
     * @param form 时序分析表单，{@link TimingAnalysisForm}
     * @return 折线图数据，{@link LineChartVO}
     */
    LineChartVO analysisByLineChart(TimingAnalysisForm form);
}
