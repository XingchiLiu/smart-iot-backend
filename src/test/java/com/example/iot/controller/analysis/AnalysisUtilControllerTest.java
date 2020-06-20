package com.example.iot.controller.analysis;

import com.alibaba.fastjson.JSON;
import com.example.iot.controller.VO.ResultVO;
import com.example.iot.controller.VO.analysis.ChannelDataFieldVO;
import com.example.iot.controller.VO.analysis.ChannelDataVO;
import com.example.iot.controller.VO.analysis.DeviceDataVO;
import com.example.iot.service.analysis.AnalysisUtilService;
import com.example.iot.util.FieldType;
import org.junit.jupiter.api.BeforeAll;
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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AnalysisUtilControllerTest {
    private MockMvc mockMvc;
    @Mock
    private AnalysisUtilService analysisUtilService;
    @InjectMocks
    private AnalysisUtilController analysisUtilController;

    private static List<DeviceDataVO> deviceDataVOs;

    @BeforeAll
    static void setUpAll() {
        ChannelDataFieldVO channelDataFieldVO = new ChannelDataFieldVO(1,
                "field-test", FieldType.DECIMAL);
        ChannelDataVO channelDataVO = new ChannelDataVO(1,
                "channel-test", Collections.singletonList(channelDataFieldVO));
        DeviceDataVO deviceDataVO = new DeviceDataVO(1,
                "device-test", Collections.singletonList(channelDataVO));
        deviceDataVOs = Collections.singletonList(deviceDataVO);
    }

    @BeforeEach
    void setUpMethod() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(analysisUtilController).build();

        Mockito.when(analysisUtilService.getAllDevicesInfo()).thenReturn(deviceDataVOs);
    }

    @Test
    void getAllDevicesInfo() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/analysis/util/device/all");
        try {
            String str = mockMvc.perform(request)
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
            assertEquals(deviceDataVOs.toString(),
                    JSON.parseArray(JSON.parseObject(str, ResultVO.class).getData().toString(), DeviceDataVO.class).toString());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}