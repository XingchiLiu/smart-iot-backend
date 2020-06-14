package com.example.iot.domain.analysis;

import com.example.iot.controller.VO.analysis.OnlineAnalysisTaskForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @author zhm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnlineAnalysisTask {
    private Integer taskId;
    private Integer modelId;
    private String name;
    private String description;

    public OnlineAnalysisTask(OnlineAnalysisTaskForm taskForm) {
        BeanUtils.copyProperties(taskForm, this);
    }
}
