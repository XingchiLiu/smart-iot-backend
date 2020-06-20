package com.example.iot.controller.analysis;

import com.alibaba.fastjson.JSON;
import com.example.iot.controller.VO.ResultVO;
import com.example.iot.controller.VO.analysis.ModelVO;
import com.example.iot.controller.VO.analysis.OnlineAnalysisTaskVO;
import com.example.iot.controller.VO.analysis.OperatorVO;
import com.example.iot.service.analysis.OnlineAnalysisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OnlineAnalysisControllerTest {
    private MockMvc mockMvc;
    @Mock
    private OnlineAnalysisService onlineAnalysisService;
    @InjectMocks
    private OnlineAnalysisController onlineAnalysisController;


    @BeforeEach
    void setUpMethod() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(onlineAnalysisController).build();
    }


    @Test
    void getAllPMMLModel() {
        List<ModelVO> modelVOs = Arrays.asList(
                new ModelVO(1, "model-test1", "model demo"),
                new ModelVO(2, "model-test2", "model demo")
        );
        Mockito.when(onlineAnalysisService.getAllPMMLModel()).thenReturn(modelVOs);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/analysis/online/model/all");
        try {
            String str = mockMvc.perform(request)
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
            assertEquals(modelVOs.toString(),
                    JSON.parseArray(JSON.parseObject(str, ResultVO.class).getData().toString(), ModelVO.class).toString());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }


    @Test
    void getAllOperator() {
        List<OperatorVO> operatorVOs = Arrays.asList(
                new OperatorVO(1, "operator-test1", "operator demo",
                        "var func = function() { return 1; }", "func"),
                new OperatorVO(2, "operator-test2", "operator demo",
                        "var func = function() { return 2; }", "func")
        );
        Mockito.when(onlineAnalysisService.getAllOperator()).thenReturn(operatorVOs);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/analysis/online/operator/all");
        try {
            String str = mockMvc.perform(request)
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
            assertEquals(operatorVOs.toString(),
                    JSON.parseArray(JSON.parseObject(str, ResultVO.class).getData().toString(), OperatorVO.class).toString());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }


    @Test
    void getAllTaskBrief() {
        List<OnlineAnalysisTaskVO> taskVOs = Arrays.asList(
                new OnlineAnalysisTaskVO(1, "task-test1", "task demo"),
                new OnlineAnalysisTaskVO(2, "task-test2", "task demo")
        );
        Mockito.when(onlineAnalysisService.getAllTask()).thenReturn(taskVOs);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/analysis/online/task/all");
        try {
            String str = mockMvc.perform(request)
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
            assertEquals(taskVOs.toString(),
                    JSON.parseArray(JSON.parseObject(str, ResultVO.class).getData().toString(), OnlineAnalysisTaskVO.class).toString());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}