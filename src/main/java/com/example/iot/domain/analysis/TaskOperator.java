package com.example.iot.domain.analysis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskOperator {
    private Integer taskId;
    private Integer inputFieldId;
    private Integer operatorId;
}
