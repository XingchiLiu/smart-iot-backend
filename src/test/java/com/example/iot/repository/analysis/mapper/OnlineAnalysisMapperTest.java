package com.example.iot.repository.analysis.mapper;

import com.example.iot.domain.analysis.Model;
import com.example.iot.domain.analysis.OnlineAnalysisTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OnlineAnalysisMapperTest {
    @Resource
    private OnlineAnalysisMapper onlineAnalysisMapper;
    @Resource
    private ModelMapper modelMapper;
    private Integer modelId;

    @BeforeEach
    void insertModel() {
        String name = "model-test";
        String description = "model demo";
        String file = "model.pmml";
        Model model = new Model(null, name, description, file);
        modelMapper.insertModel(model);

        modelId = model.getModelId();
        assertNotNull(modelId);
        Model actualModel = modelMapper.getModelById(modelId);
        assertEquals(model, actualModel);
    }

    @Test
    void getAllTask() {
        List<OnlineAnalysisTask> tasks = onlineAnalysisMapper.getAllTask();
        assertNotNull(tasks);
        if (tasks.size() != 0) {
            OnlineAnalysisTask task = tasks.get(0);
            assertNotNull(task.getTaskId());
            assertNotNull(task.getModelId());
            assertNotNull(task.getName());
            assertNotNull(task.getDescription());
        }
    }

    @Test
    void insertTask() {
        String taskName = "task-test";
        String taskDescription = "task demo";
        OnlineAnalysisTask task = new OnlineAnalysisTask(null, modelId, taskName, taskDescription);
        Integer affectedLines = onlineAnalysisMapper.insertTask(task);
        assertEquals(affectedLines, 1);
        Integer taskId = task.getTaskId();
        assertNotNull(taskId);
    }

    @Test
    void insertTaskWithId() {
        String taskName = "task-test";
        String taskDescription = "task demo";
        OnlineAnalysisTask task = new OnlineAnalysisTask(null, modelId, taskName, taskDescription);
        Integer affectedLines = onlineAnalysisMapper.insertTaskWithId(task);
        assertEquals(affectedLines, 1);
        Integer taskId = task.getTaskId();
        assertNull(taskId);
    }

    @Test
    void deleteTask() {
        String taskName = "task-test";
        String taskDescription = "task demo";
        OnlineAnalysisTask task = new OnlineAnalysisTask(null, modelId, taskName, taskDescription);
        Integer affectedLines = onlineAnalysisMapper.insertTask(task);
        assertEquals(affectedLines, 1);

        affectedLines = onlineAnalysisMapper.deleteTask(task.getTaskId());
        assertEquals(affectedLines, 1);
    }
}