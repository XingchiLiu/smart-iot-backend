package com.example.iot.repository.analysis.mapper;

import com.example.iot.domain.analysis.FuncParam;
import com.example.iot.domain.analysis.OnlineAnalysisTask;
import com.example.iot.domain.analysis.OnlineAnalysisTaskDetail;
import com.example.iot.domain.analysis.TaskOperator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhm
 */
@Mapper
public interface OnlineAnalysisMapper {
    /**
     * 获取所有实时分析任务
     *
     * @return 实时分析任务列表，{@link OnlineAnalysisTask}
     */
    List<OnlineAnalysisTask> getAllTask();

    /**
     * 创建实时分析任务
     *
     * @param task 实时分析任务
     * @return 影响的行数
     */
    Integer insertTask(OnlineAnalysisTask task);

    /**
     * 创建实时分析任务
     *
     * @param task 实时分析任务
     * @return 影响的行数
     */
    Integer insertTaskWithId(OnlineAnalysisTask task);

    /**
     * 删除实时分析任务
     *
     * @param taskId 任务id
     * @return 影响的行数
     */
    Integer deleteTask(@Param("taskId") Integer taskId);

    /**
     * 保存某个任务关联的数据通道
     *
     * @param taskId     任务id
     * @param channelIds 数据通道id列表
     * @return 影响的行数
     */
    Integer insertTaskDataChannels(@Param("taskId") Integer taskId, @Param("channelIds") List<Integer> channelIds);

    /**
     * 保存任务输入字段对应的算子
     *
     * @param taskOperators 任务算子列表
     * @return 影响的行数
     */
    Integer insertTaskOperators(@Param("taskOperators") List<TaskOperator> taskOperators);

    /**
     * 保存任务算子需要的数据通道字段
     *
     * @param funcParams 任务算子需要的数据通道字段
     * @return 影响的行数
     */
    Integer insertFuncParams(@Param("funcParams") List<FuncParam> funcParams);

    /**
     * 获取任务详情
     *
     * @param taskId 任务id
     * @return 实时分析任务详情，{@link OnlineAnalysisTaskDetail}
     */
    OnlineAnalysisTaskDetail getTaskDetail(@Param("taskId") Integer taskId);

    /**
     * 获取任务的输入字段
     *
     * @param taskId  任务id
     * @param modelId 模型id
     * @return 任务的输入字段列表
     */
    List<OnlineAnalysisTaskDetail.InputFunc> getTaskInputFields(@Param("taskId") Integer taskId,
                                                                @Param("modelId") Integer modelId);
}
