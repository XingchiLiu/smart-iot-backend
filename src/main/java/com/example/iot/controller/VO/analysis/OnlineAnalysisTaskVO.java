package com.example.iot.controller.VO.analysis;

import com.example.iot.domain.analysis.OnlineAnalysisTask;
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
public class OnlineAnalysisTaskVO {
    private Integer taskId;
    private String name;
    private String description;

    public OnlineAnalysisTaskVO(OnlineAnalysisTask task) {
        BeanUtils.copyProperties(task, this);
    }
}
