package com.example.iot.repository.analysis;

import com.example.iot.domain.analysis.Operator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhm
 */
@Mapper
public interface OperatorMapper {
    /**
     * 获取所有算子
     *
     * @return 算子列表，{@link Operator}
     */
    List<Operator> getAllOperator();

    /**
     * 根据id获取算子
     *
     * @param operatorId 算子id
     * @return 算子
     */
    Operator getOperatorById(@Param("operatorId") Integer operatorId);

    /**
     * 保存算子
     *
     * @param operator 算子
     * @return 影响的行数
     */
    Integer insertOperator(Operator operator);

    /**
     * 修改算子
     *
     * @param operator 算子
     * @return 影响的行数
     */
    Integer updateOperator(Operator operator);

    /**
     * 删除算子
     *
     * @param operatorId 算子id
     * @return 影响的行数
     */
    Integer deleteOperator(@Param("operatorId") Integer operatorId);
}
