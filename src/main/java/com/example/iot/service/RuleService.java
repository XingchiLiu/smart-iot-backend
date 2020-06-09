package com.example.iot.service;

import com.example.iot.controller.VO.RuleForm;
import com.example.iot.domain.Rule;
import com.example.iot.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Karson
 * @CreateDate 2020/6/7 19:34
 */
@Service
public class RuleService {
    @Autowired
    RuleRepository ruleRepository;

    public ArrayList<Rule> getAllByDeviceId(int deviceId){
        return ruleRepository.getAllByDeviceId(deviceId);
    }

    public int addRule(RuleForm ruleForm) {
        Rule rule = new Rule();
        rule.setDeviceId(ruleForm.getDeviceId());
        rule.setName(ruleForm.getName());
        rule.setDescription(ruleForm.getDescription());
        rule.setCreateTime(new Date());
        rule.setUpdateTime(new Date());
        rule.setRuleType(ruleForm.getRuleType());
        rule.setFieldName(ruleForm.getFieldName());
        rule.setThresholdVal(ruleForm.getThresholdVal());
        Rule result =  ruleRepository.save(rule);
        return result.getId();
    }
}
