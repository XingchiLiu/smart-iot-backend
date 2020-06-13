package com.example.iot.repository.analysis;

import com.example.iot.domain.analysis.Model;
import com.example.iot.domain.analysis.ModelField;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhm
 */
@Mapper
public interface ModelMapper {
    /**
     * 获取所有模型
     *
     * @return 模型列表，{@link Model}
     */
    List<Model> getAllModels();

    /**
     * 根据id获取模型
     *
     * @param modelId 模型id
     * @return 模型
     */
    Model getModelById(@Param("modelId") Integer modelId);

    /**
     * 插入模型数据
     *
     * @param model 模型
     * @return 影响的行数
     */
    Integer insertModel(Model model);

    /**
     * 删除模型数据
     *
     * @param modelId 模型id
     * @return 影响的行数
     */
    Integer deleteModel(@Param("modelId") Integer modelId);

    /**
     * 查询某个输入字段
     *
     * @param fieldId 模型字段id
     * @return 模型输入字段
     */
    ModelField getInputFieldById(@Param("fieldId") Integer fieldId);

    /**
     * 查询模型的所有输入字段
     *
     * @param modelId 模型id
     * @return 模型输入字段列表
     */
    List<ModelField> getInputFieldsByModelId(@Param("modelId") Integer modelId);

    /**
     * 插入模型输入字段
     *
     * @param fields 模型输入字段列表
     * @return 影响的行数
     */
    Integer insertInputFields(@Param("fields") List<ModelField> fields);
}
