package com.example.iot.controller.VO.analysis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author zhm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnlineAnalysisTaskResult {
    /**
     * 任务结果
     */
    private Map<String, ?> result;
}
