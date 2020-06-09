package com.example.iot.service.RuleService;

import com.alibaba.fastjson.JSONObject;
import com.example.iot.controller.VO.RuleForm;
import com.example.iot.domain.Rule;
import com.example.iot.repository.RuleRepository;
import com.example.iot.util.CompareUtil;
import com.example.iot.util.RuleActionType;
import com.example.iot.util.RuleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author Karson
 * @CreateDate 2020/6/7 19:34
 */
@Service
public class RuleService {
    @Autowired
    RuleRepository ruleRepository;

    public void filterAndExecute(JSONObject data) {
        int deviceId = data.getIntValue("deviceId");
        ArrayList<Rule> rules = ruleRepository.getAllByDeviceId(deviceId);
        for (Rule rule : rules) {
            double val = data.getDoubleValue(rule.getFieldName());
            double threshold = data.getDoubleValue("thresholdVal");
            RuleType ruleType = RuleType.valueOf(data.getString("ruleType"));
            Method compare = null;
            boolean result = false;
            try {
                compare = CompareUtil.class.getMethod(ruleType.toString(), double.class, double.class);
                result = (boolean) compare.invoke(CompareUtil.class, val, threshold);

                if (result) {
                    Action action = ActionFactory.getAction(RuleActionType.valueOf(data.getString("ruleActionType")), data.getString("target"), data);
                    action.execute();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Rule> getAllByDeviceId(int deviceId) {
        return ruleRepository.getAllByDeviceId(deviceId);
    }

    public int addRule(RuleForm ruleForm) {
        Rule rule = createRuleFromRuleForm(ruleForm);

        Rule result = ruleRepository.save(rule);
        return result.getId();
    }

    public int updateRule(RuleForm ruleForm) {
        Rule rule = createRuleFromRuleForm(ruleForm);
        rule.setId(ruleForm.getId());

        Rule result = ruleRepository.save(rule);
        return result.getId();
    }

    private Rule createRuleFromRuleForm(RuleForm ruleForm) {
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
