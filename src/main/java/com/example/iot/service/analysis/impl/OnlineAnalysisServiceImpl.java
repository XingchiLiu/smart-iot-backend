package com.example.iot.service.analysis.impl;

import com.example.iot.controller.VO.analysis.ModelVO;
import com.example.iot.controller.VO.analysis.OperatorForm;
import com.example.iot.controller.VO.analysis.OperatorVO;
import com.example.iot.domain.analysis.Model;
import com.example.iot.domain.analysis.ModelField;
import com.example.iot.domain.analysis.Operator;
import com.example.iot.repository.analysis.ModelMapper;
import com.example.iot.repository.analysis.OperatorMapper;
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
    @Resource
    private OperatorMapper operatorMapper;

    /*------ 模型 ------*/

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
                while (cnt > 0 && !deletePMMLModelOnDisk(filename)) {
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
                while (cnt > 0 && !deletePMMLModel(model.getModelId())) {
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

    /*------ 算子 ------*/

    @Override
    public List<OperatorVO> getAllOperator() {
        try {
            List<Operator> operators = operatorMapper.getAllOperator();
            if (operators == null) {
                return null;
            }
            return operators.stream().map(OperatorVO::new).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Server Error: getAllOperator()");
            return null;
        }
    }

    @Override
    public boolean saveOperator(OperatorForm operatorForm) {
        try {
            Operator operator = new Operator(operatorForm);
            Integer affectedLines = operatorMapper.insertOperator(operator);
            if (affectedLines <= 0) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Server Error: saveOperator(" + operatorForm.toString() + ")");
            return false;
        }
        return true;
    }

    @Override
    public boolean modifyOperator(Integer operatorId, OperatorForm operatorForm) {
        try {
            Operator operator = new Operator(operatorId, operatorForm);
            Integer affectedLines = operatorMapper.updateOperator(operator);
            if (affectedLines <= 0) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Server Error: saveOperator(" + operatorId + ", " + operatorForm.toString() + ")");
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteOperator(Integer operatorId) {
        try {
            Integer affectedLines = operatorMapper.deleteOperator(operatorId);
            if (affectedLines <= 0) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Server Error: deleteOperator(" + operatorId + ")");
            return false;
        }
        return true;
    }
}
