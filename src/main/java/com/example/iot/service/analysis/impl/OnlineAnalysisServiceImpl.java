package com.example.iot.service.analysis.impl;

import com.example.iot.controller.VO.analysis.ModelVO;
import com.example.iot.domain.analysis.Model;
import com.example.iot.domain.analysis.ModelField;
import com.example.iot.repository.analysis.ModelMapper;
import com.example.iot.service.analysis.OnlineAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.InputField;
import org.jpmml.evaluator.LoadingModelEvaluatorBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhm
 */
@Slf4j
@Service
public class OnlineAnalysisServiceImpl implements OnlineAnalysisService {
    public static final String SEPARATOR = File.separator;
    public static final String SAVE_DIRECTORY = System.getProperty("user.home") + SEPARATOR + "models";

    @Resource
    private ModelMapper modelMapper;

    @Override
    public boolean fileExists(String filename) {
        try {
            String filePath = SAVE_DIRECTORY + SEPARATOR + filename;
            File file = new File(filePath);
            return file.exists();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Server Error: getAllPMMLModel(" + filename + ")");
            return true;
        }
    }

    @Override
    public List<ModelVO> getAllPMMLModel() {
        try {
            List<Model> models = modelMapper.getAllModels();
            if (models == null) {
                return null;
            }
            return models.stream().map(ModelVO::new).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Server Error: getAllPMMLModel()");
            return null;
        }
    }

    @Override
    public boolean savePMMLModel(MultipartFile file, String name, String description) {
        try {
            // Step 1: 将模型保存到磁盘
            if (file == null || file.isEmpty() || file.getOriginalFilename() == null
                    || "".equals(file.getOriginalFilename())) {
                return false;
            }
            String filename = file.getOriginalFilename();
            String filePath = SAVE_DIRECTORY + SEPARATOR + filename;
            try {
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                log.error("存储文件时发生错误: " + filePath);
                e.printStackTrace();
                return false;
            }
            // Step 2: 将模型记录保存到数据库
            Model model = new Model(null, name, description, filename);
            try {
                modelMapper.insertModel(model);
            } catch (Exception e) {
                // 尝试删除磁盘上的PMML模型文件
                int cnt = 3;
                while (cnt > 0 && deletePMMLModelOnDisk(filename)) {
                    --cnt;
                }
                log.error("将模型保存到数据库时发生错误: " + model.toString());
                e.printStackTrace();
                return false;
            }
            // Step 3: 将模型输入字段保存到数据库
            try {
                saveInputFields(model.getModelId(), filePath);
            } catch (Exception e) {
                // 尝试删除磁盘上的PMML模型文件和数据库中的模型记录
                int cnt = 3;
                while (cnt > 0 && deletePMMLModel(model.getModelId())) {
                    --cnt;
                }
                log.error("将模型输入字段保存到数据库时发生错误: " + model.toString());
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            log.error("Server Error: savePMMLModel(" + file.toString() + ", " + name + ", " + description + ")");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean saveInputFields(Integer modelId, String filePath) throws Exception {
        Evaluator evaluator = new LoadingModelEvaluatorBuilder().load(new File(filePath)).build();
        evaluator.verify();
        List<? extends InputField> inputFields = evaluator.getInputFields();
        List<ModelField> modelFields = inputFields.stream()
                .map(e -> {
                    String fieldName = e.getName().getValue();
                    String dataType = e.getDataType().value();
                    String opType = e.getOpType().value();
                    return new ModelField(null, modelId, fieldName, dataType, opType);
                })
                .collect(Collectors.toList());
        modelMapper.insertInputFields(modelFields);
        return true;
    }

    @Override
    public boolean deletePMMLModel(Integer modelId) {
        try {
            Model deletedModel = modelMapper.getModelById(modelId);
            if (deletedModel == null) {
                return false;
            }
            modelMapper.deleteModel(modelId);
            deletePMMLModelOnDisk(deletedModel.getFilename());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Server Error: deletePMMLModel(" + modelId + ")");
            return false;
        }
        return true;
    }

    private boolean deletePMMLModelOnDisk(String filename) throws Exception {
        String filePath = SAVE_DIRECTORY + SEPARATOR + filename;
        File file = new File(filePath);
        if (!file.delete()) {
            throw new Exception("Server Error: deletePMMLModelOnDisk(" + filename + ")");
        }
        return true;
    }


}
