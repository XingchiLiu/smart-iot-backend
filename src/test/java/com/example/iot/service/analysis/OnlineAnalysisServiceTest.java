package com.example.iot.service.analysis;

import com.example.iot.controller.VO.analysis.ModelVO;
import com.example.iot.controller.VO.analysis.OnlineAnalysisTaskVO;
import com.example.iot.controller.VO.analysis.OperatorVO;
import com.example.iot.domain.analysis.Model;
import com.example.iot.domain.analysis.OnlineAnalysisTask;
import com.example.iot.domain.analysis.Operator;
import com.example.iot.repository.analysis.mapper.ModelMapper;
import com.example.iot.repository.analysis.mapper.OnlineAnalysisMapper;
import com.example.iot.repository.analysis.mapper.OperatorMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OnlineAnalysisServiceTest {
    @Autowired
    private OnlineAnalysisService onlineAnalysisService;
    @Resource
    private ModelMapper modelMapper;
    @Resource
    private OperatorMapper operatorMapper;
    @Resource
    private OnlineAnalysisMapper onlineAnalysisMapper;

    @Test
    void getAllPMMLModel() {
        List<ModelVO> modelVOs = onlineAnalysisService.getAllPMMLModel();
        List<Model> models = modelMapper.getAllModels();
        assertNotNull(modelVOs);
        assertNotNull(models);
        assertTrue(models.size() >= modelVOs.size());
    }

    @Test
    void getAllOperator() {
        List<OperatorVO> operatorVOs = onlineAnalysisService.getAllOperator();
        List<Operator> operators = operatorMapper.getAllOperator();
        assertNotNull(operatorVOs);
        assertNotNull(operators);
        assertTrue(operators.size() >= operatorVOs.size());
    }


    @Test
    void getAllTask() {
        List<OnlineAnalysisTaskVO> taskVOs = onlineAnalysisService.getAllTask();
        List<OnlineAnalysisTask> tasks = onlineAnalysisMapper.getAllTask();
        assertNotNull(taskVOs);
        assertNotNull(tasks);
        assertTrue(tasks.size() >= taskVOs.size());
    }
}