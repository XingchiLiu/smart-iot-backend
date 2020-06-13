package com.example.iot.controller.analysis;

import com.example.iot.controller.VO.ResultVO;
import com.example.iot.controller.VO.analysis.ModelVO;
import com.example.iot.controller.VO.analysis.OperatorForm;
import com.example.iot.controller.VO.analysis.OperatorVO;
import com.example.iot.service.analysis.OnlineAnalysisService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhm
 */
@Api("data-analysis")
@Slf4j
@RestController
@RequestMapping("/analysis/online")
public class OnlineAnalysisController {
    public static final String CLIENT_ERROR = "客户端发生错误，请检查请求";
    public static final String SERVER_ERROR = "服务端发生错误";
    public static final String ID_ERROR = "参数id必须大于0";
    public static final String NAME_EMPTY_ERROR = "名称不能为空";
    public static final String FILE_TRANSPORT_ERROR = "文件出错，请正确上传文件";
    public static final String FILE_NAME_ERROR = "文件名错误";
    public static final String FILE_EXTENSION_ERROR = "文件后缀名错误";
    public static final String MODEL_ALREADY_EXIST = "同名的模型已存在";
    public static final String OPERATOR_CODE_EMPTY_ERROR = "算子代码不能为空";
    public static final String OPERATOR_CODE_FORMAT_ERROR = "算子格式错误";
    public static final String UPLOAD_ERROR = "上传失败";
    public static final String UPLOAD_SUCCESS = "上传成功";
    public static final String MODIFY_ERROR = "修改失败";
    public static final String MODIFY_SUCCESS = "修改成功";
    public static final String DELETE_ERROR = "删除失败";
    public static final String DELETE_SUCCESS = "删除成功";

    public static final List<String> FILE_EXTENSIONS = Arrays.asList(".pmml", ".xml");

    private OnlineAnalysisService onlineAnalysisService;

    @Autowired
    public OnlineAnalysisController(OnlineAnalysisService onlineAnalysisService) {
        this.onlineAnalysisService = onlineAnalysisService;
    }

    /**
     * 获取所有PMML模型
     *
     * @return PMML模型列表，{@link ModelVO}
     */
    @GetMapping("/model/all")
    public ResultVO getAllPMMLModel() {
        List<ModelVO> models = onlineAnalysisService.getAllPMMLModel();

        if (models == null) {
            return ResultVO.getFailed(SERVER_ERROR);
        }
        return ResultVO.getSuccess(models);
    }

    /**
     * 保存PMML模型文件
     *
     * @param file PMML模型，类型为xml文件
     * @return 保存结果
     */
    @PostMapping("/model/save")
    public ResultVO savePMMLModel(@RequestParam("file") MultipartFile file,
                                  @RequestParam("name") String name,
                                  @RequestParam(value = "description" ,defaultValue = "") String description) {
        if (name == null || "".equals(name)) {
            return ResultVO.getFailed(NAME_EMPTY_ERROR);
        }
        if (file == null || file.isEmpty()) {
            return ResultVO.getFailed(FILE_TRANSPORT_ERROR);
        }
        String fileName = file.getOriginalFilename();
        if (fileName == null || "".equals(fileName)) {
            return ResultVO.getFailed(FILE_NAME_ERROR);
        }
        if (FILE_EXTENSIONS.stream().noneMatch(fileName::endsWith)) {
            return ResultVO.getFailed(FILE_EXTENSION_ERROR);
        }
        if (onlineAnalysisService.fileExists(fileName)) {
            return ResultVO.getFailed(MODEL_ALREADY_EXIST);
        }

        boolean saveSuccess = onlineAnalysisService.savePMMLModel(file, name, description);

        if (!saveSuccess) {
            return ResultVO.getFailed(UPLOAD_ERROR);
        }
        return ResultVO.getSuccess(UPLOAD_SUCCESS);
    }

    /**
     * 删除PMML模型
     *
     * @param modelId 模型id
     * @return 删除结果
     */
    @GetMapping("/model/delete")
    public ResultVO deletePMMLModel(@RequestParam("modelId") Integer modelId) {
        if (modelId == null || modelId <= 0) {
            return ResultVO.getFailed(ID_ERROR);
        }

        boolean deleteSuccess = onlineAnalysisService.deletePMMLModel(modelId);

        if (!deleteSuccess) {
            return ResultVO.getFailed(DELETE_ERROR);
        }
        return ResultVO.getSuccess(DELETE_SUCCESS);
    }

    /**
     * 获取所有算子
     *
     * @return 算子列表，{@link OperatorVO}
     */
    @GetMapping("/operator/all")
    public ResultVO getAllOperator() {
        List<OperatorVO> operators = onlineAnalysisService.getAllOperator();

        if (operators == null) {
            return ResultVO.getFailed(SERVER_ERROR);
        }
        return ResultVO.getSuccess(operators);
    }

    /**
     * 保存算子
     *
     * @param operatorForm 算子
     * @return 保存结果
     */
    @PostMapping("/operator/create")
    public ResultVO createOperator(@RequestBody OperatorForm operatorForm) {
        if (operatorForm == null) {
            return ResultVO.getFailed(CLIENT_ERROR);
        }
        if (operatorForm.getName() == null) {
            return ResultVO.getFailed(NAME_EMPTY_ERROR);
        }
        if (operatorForm.getJsCode() == null || "".equals(operatorForm.getJsCode())) {
            return ResultVO.getFailed(OPERATOR_CODE_EMPTY_ERROR);
        }

        if (operatorForm.getDescription() == null) {
            operatorForm.setDescription("");
        }
        boolean saveSuccess = onlineAnalysisService.saveOperator(operatorForm);

        if (!saveSuccess) {
            return ResultVO.getFailed(UPLOAD_ERROR);
        }
        return ResultVO.getSuccess(UPLOAD_SUCCESS);
    }

    /**
     * 修改算子
     *
     * @param operatorId   算子id
     * @param operatorForm 算子
     * @return 修改结果
     */
    @PostMapping("/operator/modify")
    public ResultVO modifyOperator(@RequestParam("operatorId") Integer operatorId,
                                   @RequestBody OperatorForm operatorForm) {
        if (operatorId == null || operatorId <= 0) {
            return ResultVO.getFailed(ID_ERROR);
        }
        if (operatorForm.getName() == null) {
            return ResultVO.getFailed(NAME_EMPTY_ERROR);
        }
        if (operatorForm.getJsCode() == null || "".equals(operatorForm.getJsCode())) {
            return ResultVO.getFailed(OPERATOR_CODE_EMPTY_ERROR);
        }

        if (operatorForm.getDescription() == null) {
            operatorForm.setDescription("");
        }
        boolean saveSuccess = onlineAnalysisService.modifyOperator(operatorId, operatorForm);

        if (!saveSuccess) {
            return ResultVO.getFailed(MODIFY_ERROR);
        }
        return ResultVO.getSuccess(MODIFY_SUCCESS);
    }

    /**
     * 删除算子
     *
     * @param operatorId 算子id
     * @return 删除结果
     */
    @GetMapping("/operator/delete")
    public ResultVO deleteOperator(@RequestParam("operatorId") Integer operatorId) {
        if (operatorId == null || operatorId <= 0) {
            return ResultVO.getFailed(ID_ERROR);
        }

        boolean deleteSuccess = onlineAnalysisService.deleteOperator(operatorId);

        if (!deleteSuccess) {
            return ResultVO.getFailed(DELETE_ERROR);
        }
        return ResultVO.getSuccess(DELETE_SUCCESS);
    }


    @GetMapping("/task/all")
    public ResultVO getAllTask() {
        return null;
    }

    @PostMapping("/task/create")
    public ResultVO createTask() {
        return null;
    }

    @PostMapping("/task/modify")
    public ResultVO modifyTask() {
        return null;
    }

    @GetMapping("/task/delete")
    public ResultVO deleteTask() {
        return null;
    }

    @GetMapping("/task/execute")
    public ResultVO executeTask() {
        return null;
    }
}
