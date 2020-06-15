package com.example.iot.controller.analysis;

import com.example.iot.controller.VO.ResultVO;
import com.example.iot.controller.VO.analysis.LineChartVO;
import com.example.iot.controller.VO.analysis.TimingAnalysisForm;
import com.example.iot.service.analysis.TimingAnalysisService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhm
 */
@Api("data-analysis")
@Slf4j
@RestController
@RequestMapping("/analysis/timing")
public class TimingAnalysisController {
    public static final String TIME_PARAM_ERROR = "时间参数错误";
    public static final String TIME_INTERVAL_PARAM_ERROR = "时间间隔参数错误";
    public static final String MEASURE_POINT_PARAM_ERROR = "监测点参数错误";
    public static final String TIMING_ANALYSIS_ERROR = "时序分析出错，请稍后再试";

    private TimingAnalysisService timingAnalysisService;

    @Autowired
    public TimingAnalysisController(TimingAnalysisService timingAnalysisService) {
        this.timingAnalysisService = timingAnalysisService;
    }

    /**
     * 使用折线图显示根据时间段聚合后的设备信息
     *
     * @param form 时序分析表单，{@link TimingAnalysisForm}
     * @return 折线图数据，{@link LineChartVO}
     */
    @PostMapping("/line-chart")
    public ResultVO analysisByLineChart(@RequestBody TimingAnalysisForm form) {
        if (form.getIntervalMinutes() == null || form.getIntervalMinutes() <= 0) {
            return ResultVO.getFailed(TIME_INTERVAL_PARAM_ERROR);
        }
        if (form.getStartTime() == null || form.getEndTime() == null
                || form.getStartTime().isAfter(form.getEndTime())) {
            return ResultVO.getFailed(TIME_PARAM_ERROR);
        }
        if (form.hasIdsError()) {
            return ResultVO.getFailed(MEASURE_POINT_PARAM_ERROR);
        }

        LineChartVO lineChart = timingAnalysisService.analysisByLineChart(form);

        if (lineChart == null) {
            return ResultVO.getFailed(TIMING_ANALYSIS_ERROR);
        }
        return ResultVO.getSuccess(lineChart);
    }
}
