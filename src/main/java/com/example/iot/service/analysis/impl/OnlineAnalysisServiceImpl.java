package com.example.iot.service.analysis.impl;

import com.example.iot.controller.VO.analysis.*;
import com.example.iot.domain.DeviceMessage;
import com.example.iot.domain.analysis.*;
import com.example.iot.domain.analysis.ModelField;
import com.example.iot.domain.analysis.OnlineAnalysisTaskDetail.*;
import com.example.iot.repository.analysis.mapper.ModelMapper;
import com.example.iot.repository.analysis.mapper.OnlineAnalysisMapper;
import com.example.iot.repository.analysis.mapper.OperatorMapper;
import com.example.iot.repository.analysis.repo.DeviceMessageRepo;
import com.example.iot.service.analysis.OnlineAnalysisService;
import com.example.iot.util.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.dmg.pmml.FieldName;
import org.jpmml.evaluator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.*;
import java.util.*;
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
    @Resource
    private OnlineAnalysisMapper onlineAnalysisMapper;

    private DeviceMessageRepo deviceMessageRepo;

    @Autowired
    public OnlineAnalysisServiceImpl(DeviceMessageRepo deviceMessageRepo) {
        this.deviceMessageRepo = deviceMessageRepo;
    }

    /*------ 模型 ------*/

    @Override
    public boolean fileExists(String filename) {
        try {
            String filePath = SAVE_DIRECTORY + SEPARATOR + filename;
            File file = new File(filePath);
            return file.exists();
        } catch (Exception e) {
            log.error("Server Error: getAllPMMLModel(" + filename + ")");
            e.printStackTrace();
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
            log.error("Server Error: getAllPMMLModel()");
            e.printStackTrace();
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
            log.error("Server Error: deletePMMLModel(" + modelId + ")");
            e.printStackTrace();
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

    @Override
    public List<ModelFieldVO> getPMMLModelInputFields(Integer modelId) {
        try {
            List<ModelField> modelFields = modelMapper.getInputFieldsByModelId(modelId);
            if (modelFields == null) {
                return null;
            }
            return modelFields.stream()
                    .map(ModelFieldVO::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Server Error: getPMMLModelInputFields(" + modelId + ")");
            e.printStackTrace();
            return null;
        }
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
            log.error("Server Error: getAllOperator()");
            e.printStackTrace();
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
            log.error("Server Error: saveOperator(" + operatorForm.toString() + ")");
            e.printStackTrace();
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
            log.error("Server Error: modifyOperator(" + operatorId + ", " + operatorForm.toString() + ")");
            e.printStackTrace();
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
            log.error("Server Error: deleteOperator(" + operatorId + ")");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /*------ 实时分析任务 ------*/

    @Override
    public List<OnlineAnalysisTaskVO> getAllTask() {
        try {
            List<OnlineAnalysisTask> tasks = onlineAnalysisMapper.getAllTask();
            if (tasks == null) {
                return null;
            }
            return tasks.stream().map(OnlineAnalysisTaskVO::new).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Server Error: getAllTask()");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveTask(OnlineAnalysisTaskForm taskForm) {
        try {
            return saveTask(null, taskForm);
        } catch (Exception e) {
            log.error("Server Error: saveTask(" + taskForm.toString() + ")");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modifyTask(Integer taskId, OnlineAnalysisTaskForm taskForm) {
        try {
            //Step 1: 删除旧任务
            boolean deleteSuccess = deleteTask(taskId);
            //Step 2: 插入修改后的任务，并保持id一致性
            if (!deleteSuccess) {
                return false;
            }
            return saveTask(taskId, taskForm);
        } catch (Exception e) {
            deleteTask(taskId);
            log.error("Server Error: modifyTask(" + taskId + ", " + taskForm.toString() + ")");
            e.printStackTrace();
            return false;
        }
    }

    private boolean saveTask(Integer taskId, OnlineAnalysisTaskForm taskForm) {
        // Step 1: 保存任务基本信息
        OnlineAnalysisTask task = new OnlineAnalysisTask(taskForm);
        Integer affectedLines;
        if (taskId == null) {
            affectedLines = onlineAnalysisMapper.insertTask(task);
        } else {
            task.setTaskId(taskId);
            affectedLines = onlineAnalysisMapper.insertTaskWithId(task);
        }

        if (affectedLines <= 0) {
            return false;
        }
        Integer finalTaskId = task.getTaskId();
        try {
            // Step 2: 保存任务关联的数据通道
            onlineAnalysisMapper.insertTaskDataChannels(finalTaskId, taskForm.getChannelIds());
            // Step 3: 保存输入字段对应的算子
            List<TaskOperator> taskOperators = taskForm.getFunctions().stream()
                    .map(function -> new TaskOperator(finalTaskId, function.getInputFieldId(), function.getOperatorId()))
                    .collect(Collectors.toList());
            onlineAnalysisMapper.insertTaskOperators(taskOperators);
            // Step 4: 保存输入字段对应的数据通道字段
            List<FuncParam> funcParams = taskForm.getFunctions().stream()
                    .map(function -> new FuncParam(finalTaskId, function.getInputFieldId(), function.getChannelFieldIds()))
                    .collect(Collectors.toList());
            onlineAnalysisMapper.insertFuncParams(funcParams);
        } catch (Exception e) {
            deleteTask(finalTaskId);
            log.error("Server Error: saveTask(" + taskId + ", " + taskForm.toString() + ")");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteTask(Integer taskId) {
        try {
            Integer affectedLines = onlineAnalysisMapper.deleteTask(taskId);
            if (affectedLines <= 0) {
                return false;
            }
        } catch (Exception e) {
            log.error("Server Error: deleteTask(" + taskId + ")");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public OnlineAnalysisTaskDetailVO getTaskDetail(Integer taskId) {
        try {
            OnlineAnalysisTaskDetail taskDetail = getTaskDetailInfo(taskId);
            if (taskDetail == null) {
                return null;
            }
            return new OnlineAnalysisTaskDetailVO(taskDetail);
        } catch (Exception e) {
            log.error("Server Error: getTaskDetail(" + taskId + ")");
            e.printStackTrace();
            return null;
        }
    }

    private OnlineAnalysisTaskDetail getTaskDetailInfo(Integer taskId) {
        //Step 1: 实时分析任务基本信息
        OnlineAnalysisTaskDetail taskDetail = onlineAnalysisMapper.getTaskDetail(taskId);
        if (taskDetail == null || taskDetail.getModel() == null
                || taskDetail.getChannels() == null) {
            return null;
        }
        //Step 2: 根据taskId和modelId获取输入字段
        List<InputFunc> inputFuncs = onlineAnalysisMapper
                .getTaskInputFields(taskId, taskDetail.getModel().getModelId());
        if (inputFuncs == null) {
            return null;
        }
        taskDetail.setInputFuncs(inputFuncs);
        return taskDetail;
    }

    @Override
    public OnlineAnalysisTaskResult executeTask(Integer taskId) {
        OnlineAnalysisTaskResult result;
        try {
            OnlineAnalysisTaskDetail taskDetail = getTaskDetailInfo(taskId);
            if (taskDetail == null) {
                return null;
            }
            // Step 1: 获取数据通道最近的数据
            if (taskDetail.getChannels() == null) {
                return null;
            }
            Map<String, DeviceMessage> channelMsgs = new HashMap<>();
            try {
                List<String> channelIds = taskDetail.getChannels().stream()
                        .map(deviceChannel -> String.valueOf(deviceChannel.getId()))
                        .collect(Collectors.toList());

                for (String channelId : channelIds) {
                    DeviceMessage msg = deviceMessageRepo.getLastMsgByTopic(channelId);
                    if (msg == null) {
                        return null;
                    }
                    channelMsgs.put(channelId, msg);
                }
            } catch (Exception e) {
                log.error("Server Error: executeTask(" + taskId + ") - get device message error");
                e.printStackTrace();
                return null;
            }

            // Step 2: 加载PMML模型
            if (taskDetail.getModel() == null || taskDetail.getModel().getFilename() == null) {
                return null;
            }
            Evaluator evaluator;
            try {
                String filePath = SAVE_DIRECTORY + SEPARATOR + taskDetail.getModel().getFilename();
                evaluator = new LoadingModelEvaluatorBuilder().load(new File(filePath)).build();
                evaluator.verify();
            } catch (Exception e) {
                log.error("Server Error: executeTask(" + taskId + ") - load pmml model error");
                e.printStackTrace();
                return null;
            }
            // Step 3: 通过算子对数据通道字段进行预处理
            if (taskDetail.getInputFuncs() == null) {
                return null;
            }
            Map<FieldName, FieldValue> arguments = new LinkedHashMap<>();
            try {
                List<? extends InputField> inputFields = evaluator.getInputFields();
                Map<String, InputFunc> inputFuncs = taskDetail.getInputFuncs().stream()
                        .collect(Collectors.toMap(
                                inputFunc -> inputFunc.getInputField().getFieldName(),
                                inputFunc -> inputFunc));
                for (InputField inputField : inputFields) {
                    FieldName inputFieldName = inputField.getName();
                    InputFunc inputFunc = inputFuncs.get(inputFieldName.getValue());
                    if (inputFunc == null || inputFunc.getOperator() == null
                            || inputFunc.getChannelDataFields() == null) {
                        return null;
                    }
                    // 转换算子的输入参数
                    List<Parameter> parameters = inputFunc.getChannelDataFields().stream()
                            .map(funcField -> {
                                DeviceMessage msg = channelMsgs.get(funcField.getChannelId().toString());
                                if (msg == null) {
                                    return null;
                                }
                                Object param = msg.getDataMap().get(funcField.getFieldName());
                                if (param == null) {
                                    return null;
                                }
                                return new Parameter(funcField.getIndex(), param);
                            })
                            .collect(Collectors.toList());
                    Object jsResult = runJsCode(inputFunc.getOperator().getFuncName(),
                            inputFunc.getOperator().getJsCode(), parameters);
                    FieldValue inputFieldValue = FieldValue
                            .create(inputField.getDataType(), inputField.getOpType(), jsResult);
                    arguments.put(inputFieldName, inputFieldValue);
                }
            } catch (Exception e) {
                log.error("Server Error: executeTask(" + taskId + ") - execute operator error");
                e.printStackTrace();
                return null;
            }
            // Step 4: 运行PMML模型，获得任务结果
            Map<FieldName, ?> results = evaluator.evaluate(arguments);
            Map<String, ?> resultRecord = EvaluatorUtil.decodeAll(results);
            result = new OnlineAnalysisTaskResult(resultRecord);
        } catch (Exception e) {
            log.error("Server Error: executeTask(" + taskId + ")");
            e.printStackTrace();
            return null;
        }
        return result;
    }


    private Object runJsCode(String funcName, String jsCode, List<Parameter> parameters) throws Exception {
        List<Object> params = new ArrayList<>(parameters.size());
        parameters.sort(Comparator.comparing(Parameter::getIndex));
        for (Parameter p : parameters) {
            params.add(p.getParam());
        }

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(jsCode);
        Invocable invocable = (Invocable) engine;
        return invocable.invokeFunction(funcName, params.toArray());
    }
}
