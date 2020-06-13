package com.example.iot.controller.analysis;

import com.example.iot.controller.VO.ResultVO;
import com.example.iot.controller.VO.analysis.ModelVO;
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
    public static final String SERVER_ERROR = "服务端发生错误";
    public static final String ID_ERROR = "参数id必须大于0";
    public static final String FILE_TRANSPORT_ERROR = "文件出错，请正确上传文件";
    public static final String FILE_NAME_ERROR = "文件名错误";
    public static final String FILE_EXTENSION_ERROR = "文件后缀名错误";
    public static final String MODEL_ALREADY_EXIST = "同名的模型已存在";
    public static final String UPLOAD_MODEL_ERROR = "上传模型失败";
    public static final String UPLOAD_MODEL_SUCCESS = "上传模型成功";
    public static final String DELETE_MODEL_ERROR = "删除模型失败";
    public static final String DELETE_MODEL_SUCCESS = "删除模型成功";

    public static final List<String> FILE_EXTENSIONS = Arrays.asList(".pmml", ".xml");

    private OnlineAnalysisService onlineAnalysisService;

    @Autowired
    public OnlineAnalysisController(OnlineAnalysisService onlineAnalysisService) {
        this.onlineAnalysisService = onlineAnalysisService;
    }

    /**
     * 获取所有PMML模型
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
                                  @RequestParam("description") String description) {
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
            return ResultVO.getFailed(UPLOAD_MODEL_ERROR);
        }
        return ResultVO.getSuccess(UPLOAD_MODEL_SUCCESS);
    }

    /**
     * 删除PMML模型
     * @param modelId 模型id
     * @return 删除结果
     */
    @GetMapping("/model/delete")
    public ResultVO deletePMMLModel(@RequestParam("modelId") Integer modelId) {
        if (modelId <= 0) {
            return ResultVO.getFailed(ID_ERROR);
        }

        boolean deleteSuccess = onlineAnalysisService.deletePMMLModel(modelId);

        if (!deleteSuccess) {
            return ResultVO.getFailed(DELETE_MODEL_ERROR);
        }
        return ResultVO.getSuccess(DELETE_MODEL_SUCCESS);
    }


    @GetMapping("/operator/all")
    public ResultVO getAllOperator() {
        return null;
    }

    @PostMapping("/operator/create")
    public ResultVO createOperator() {
        return null;
    }

    @PostMapping("/operator/modify")
    public ResultVO modifyOperator() {
        return null;
    }

    @GetMapping("/operator/delete")
    public ResultVO deleteOperator() {
        return null;
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
