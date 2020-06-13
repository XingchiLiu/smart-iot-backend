package com.example.iot.service.analysis;

import com.example.iot.controller.VO.analysis.ModelVO;
import com.example.iot.controller.VO.analysis.OperatorForm;
import com.example.iot.controller.VO.analysis.OperatorVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author zhm
 */
public interface OnlineAnalysisService {
    /**
     * 判断文件是否已存在
     * @param filename 文件名
     * @return true表示文件已存在
     */
    boolean fileExists(String filename);

    /**
     * 获取所有PMML模型
     * @return 模型列表，{@link ModelVO}
     */
    List<ModelVO> getAllPMMLModel();

    /**
     * 保存PMML模型
     * @param file PMML模型，类型为xml文件
     * @return true表示保存成功
     */
    boolean savePMMLModel(MultipartFile file, String name, String description);

    /**
     * 删除PMML模型
     * @param modelId 模型id
     * @return true表示删除成功
     */
    boolean deletePMMLModel(Integer modelId);

    /**
     * 获取所有算子
     *
     * @return 算子列表，{@link OperatorVO}
     */
    List<OperatorVO> getAllOperator();

    /**
     * 保存算子
     *
     * @param operatorForm 算子
     * @return 保存结果
     */
    boolean saveOperator(OperatorForm operatorForm);

    /**
     * 修改算子
     *
     * @param operatorId 算子id
     * @param operatorForm   算子
     * @return 修改结果
     */
    boolean modifyOperator(Integer operatorId, OperatorForm operatorForm);

    /**
     * 删除算子
     *
     * @param operatorId 算子id
     * @return 删除结果
     */
    boolean deleteOperator(Integer operatorId);
}
