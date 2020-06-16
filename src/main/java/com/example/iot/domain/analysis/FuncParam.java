package com.example.iot.domain.analysis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncParam {
    private Integer taskId;
    private Integer inputFieldId;
    private List<Integer> channelFieldIds;
}
