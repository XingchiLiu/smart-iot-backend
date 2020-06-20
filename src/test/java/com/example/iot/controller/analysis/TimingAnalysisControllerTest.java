package com.example.iot.controller.analysis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.iot.controller.VO.ResultVO;
import com.example.iot.controller.VO.analysis.LineChartVO;
import com.example.iot.controller.VO.analysis.LineChartVO.*;
import com.example.iot.controller.VO.analysis.TimingAnalysisForm;
import com.example.iot.service.analysis.TimingAnalysisService;
import com.example.iot.util.AggregationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TimingAnalysisControllerTest {
    private MockMvc mockMvc;

    @Mock
    private TimingAnalysisService timingAnalysisService;
    @InjectMocks
    private TimingAnalysisController timingAnalysisController;

    private static LineChartVO lineChartVO;

    @BeforeAll
    static void setUpAll() {
        List<LocalDateTime> timePoints = Arrays.asList(LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        List<Double> values = Arrays.asList(1.0, 2.0, 3.0);
        List<Metric> metrics = Arrays.asList(
                new Metric(1, 1, 1, AggregationType.AVG, values),
                new Metric(1, 1, 2, AggregationType.COUNT, values)
        );
        lineChartVO = new LineChartVO(timePoints, metrics);
    }

    @BeforeEach
    void setUpMethod() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(timingAnalysisController).build();

        Mockito.when(timingAnalysisService.analysisByLineChart(Mockito.any(TimingAnalysisForm.class)))
                .thenReturn(lineChartVO);
    }

    @Test
    void analysisByLineChart() {
        MeasurePointTest measurePoint1 = new MeasurePointTest(1, 1, 1, 2);
        MeasurePointTest measurePoint2 = new MeasurePointTest(1, 1, 2, 2);
        TimingAnalysisFormTest form = new TimingAnalysisFormTest(LocalDateTime.of(2020, 6, 1, 0, 0),
                LocalDateTime.of(2020, 6, 20, 0, 0),
                1200, Arrays.asList(measurePoint1, measurePoint2));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/analysis/timing/line-chart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(form));
        try {
            String str = mockMvc.perform(request)
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
            ResultVO result = JSON.parseObject(str, ResultVO.class);
            assertNotNull(result);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}

@Data
@AllArgsConstructor
class TimingAnalysisFormTest {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer intervalMinutes;
    private List<MeasurePointTest> measurePoints;
}

@Data
@AllArgsConstructor
class MeasurePointTest {
    private Integer deviceId;
    private Integer channelId;
    private Integer fieldId;
    private Integer aggregationType;
}