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
        Rule rule = createRuleFromRuleForm(ruleForm);

        Rule result =  ruleRepository.save(rule);
        return result.getId();
    }

    public int updateRule(RuleForm ruleForm) {
        Rule rule = createRuleFromRuleForm(ruleForm);
        rule.setId(ruleForm.getId());

        Rule result =  ruleRepository.save(rule);
        return result.getId();
    }

    private Rule createRuleFromRuleForm(RuleForm ruleForm){
        Rule rule = new Rule();
        rule.setDeviceId(ruleForm.getDeviceId());
        rule.setName(ruleForm.getName());
        rule.setDescription(ruleForm.getDescription());
        rule.setRuleType(ruleForm.getRuleType());
        rule.setFieldName(ruleForm.getFieldName());
        rule.setThresholdVal(ruleForm.getThresholdVal());
        return rule;
    }

    public void deleteRule(int ruleId) {
        ruleRepository.deleteById(ruleId);
    }
}
