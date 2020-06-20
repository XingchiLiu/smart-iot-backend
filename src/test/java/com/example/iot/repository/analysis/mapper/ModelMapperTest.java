package com.example.iot.repository.analysis.mapper;

import com.example.iot.domain.analysis.Model;
import com.example.iot.domain.analysis.ModelField;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ModelMapperTest {
    @Resource
    private ModelMapper modelMapper;

    @Test
    void getAllModels() {
        List<Model> models = modelMapper.getAllModels();
        assertNotNull(models);
        if (models.size() != 0) {
            Model model = models.get(0);
            assertNotNull(model.getModelId());
            assertNotNull(model.getName());
            assertNotNull(model.getDescription());
            assertNotNull(model.getFilename());
        }
    }

    @Test
    void getModelById() {
        Model model = modelMapper.getModelById(1);
        if (model != null) {
            assertNotNull(model.getModelId());
            assertNotNull(model.getName());
            assertNotNull(model.getDescription());
            assertNotNull(model.getFilename());
        }
    }

    @Test
    void insertModel() {
        String name = "model-test";
        String description = "model demo";
        String file = "model.pmml";
        Model model = new Model(null, name, description, file);
        modelMapper.insertModel(model);

        Integer modelId = model.getModelId();
        assertNotNull(modelId);
        Model actualModel = modelMapper.getModelById(modelId);
        assertEquals(model, actualModel);
    }

    @Test
    void deleteModel() {
        String name = "model-test";
        String description = "model demo";
        String file = "model.pmml";
        Model model = new Model(null, name, description, file);
        modelMapper.insertModel(model);

        Integer modelId = model.getModelId();
        assertNotNull(modelId);
        Model actualModel = modelMapper.getModelById(modelId);
        assertEquals(model, actualModel);

        Integer affectedLines = modelMapper.deleteModel(modelId);
        assertEquals(affectedLines, 1);
    }

    @Test
    void getInputFieldById() {
        String name = "model-test";
        String description = "model demo";
        String file = "model.pmml";
        Model model = new Model(null, name, description, file);
        modelMapper.insertModel(model);

        Integer modelId = model.getModelId();
        assertNotNull(modelId);
        Model actualModel = modelMapper.getModelById(modelId);
        assertEquals(model, actualModel);

        ModelField field1 = new ModelField(null, modelId, "field1", "float", "continuous");
        List<ModelField> fields = Collections.singletonList(field1);

        Integer affectedLines = modelMapper.insertInputFields(fields);
        assertEquals(affectedLines, 1);

        assertNotNull(field1.getFieldId());
        ModelField modelField = modelMapper.getInputFieldById(field1.getFieldId());
        assertEquals(modelField, field1);
    }

    @Test
    void getInputFieldsByModelId() {
        String name = "model-test";
        String description = "model demo";
        String file = "model.pmml";
        Model model = new Model(null, name, description, file);
        modelMapper.insertModel(model);

        Integer modelId = model.getModelId();
        assertNotNull(modelId);
        Model actualModel = modelMapper.getModelById(modelId);
        assertEquals(model, actualModel);

        ModelField field1 = new ModelField(null, modelId, "field1", "float", "continuous");
        ModelField field2 = new ModelField(null, modelId, "field2", "float", "continuous");
        List<ModelField> fields = Arrays.asList(field1, field2);

        Integer affectedLines = modelMapper.insertInputFields(fields);
        assertEquals(affectedLines, 2);

        List<ModelField> actualFields = modelMapper.getInputFieldsByModelId(modelId);
        assertEquals(actualFields, fields);
    }

    @Test
    void insertInputFields() {
        String name = "model-test";
        String description = "model demo";
        String file = "model.pmml";
        Model model = new Model(null, name, description, file);
        modelMapper.insertModel(model);

        Integer modelId = model.getModelId();
        assertNotNull(modelId);
        Model actualModel = modelMapper.getModelById(modelId);
        assertEquals(model, actualModel);

        ModelField field1 = new ModelField(null, modelId, "field1", "float", "continuous");
        ModelField field2 = new ModelField(null, modelId, "field2", "float", "continuous");
        List<ModelField> fields = Arrays.asList(field1, field2);

        Integer affectedLines = modelMapper.insertInputFields(fields);
        assertEquals(affectedLines, 2);
    }
}