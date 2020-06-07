package com.example.iot.service;

import com.example.iot.controller.VO.ResultVO;
import com.example.iot.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Karson
 * @CreateDate 2020/6/7 19:34
 */
@Service
public class RuleService {
    @Autowired
    RuleRepository ruleRepository;

    public ResultVO getAllByDeviceId(int deviceId){
        return ResultVO.getSuccess("成功",ruleRepository.getAllByDeviceId(deviceId));
    }
}
