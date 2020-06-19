package com.example.iot.service.RuleService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.iot.controller.VO.rule.RuleForm;
import com.example.iot.domain.rule.Rule;
import com.example.iot.repository.rule.RuleRepository;
import com.example.iot.service.RuleService.action.Action;
import com.example.iot.service.RuleService.action.ActionFactory;
import com.example.iot.util.CompareUtil;
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
    @Autowired
    RuleLogService ruleLogService;

    public void filterAndExecute(JSONObject data) {
        int deviceId = data.getIntValue("deviceId");
        ArrayList<Rule> rules = ruleRepository.getAllByDeviceId(deviceId);
        for (Rule rule : rules) {
            JSONArray messages = data.getJSONArray("data");
            double val = 0;
            boolean flag = true;
            String key = rule.getFieldName();
            for (int i = 0; i < messages.size(); i++) {
                JSONObject message = messages.getJSONObject(i);
                if(message.containsKey(key)){
                    val = message.getDoubleValue(key);
                    flag = false;
                }
            }
            if(flag){
                continue;
            }

            double threshold = rule.getThresholdVal();
            RuleType ruleType = rule.getRuleType();
            Method compare = null;
            boolean result = false;
            try {
                compare = CompareUtil.class.getMethod(ruleType.toString(), double.class, double.class);
                result = (boolean) compare.invoke(CompareUtil.class, val, threshold);

                if (result) {
                    try {
                        Action action = ActionFactory.getAction(rule.getRuleActionType(), rule.getTarget(), data);
                        action.execute();
                        ruleLogService.saveSuccessRuleLog(rule.getId(), data);
                    } catch (Exception e) {
                        ruleLogService.saveFailureRuleLog(rule.getId(), data);
                    }
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
        rule.setRuleActionType(ruleForm.getRuleActionType());
        rule.setTarget(ruleForm.getTarget());
        return rule;
    }

    public void deleteRule(int ruleId) {
        ruleRepository.deleteById(ruleId);
    }

    public ArrayList<Rule> getAll() {
        return ruleRepository.get();
    }
}
