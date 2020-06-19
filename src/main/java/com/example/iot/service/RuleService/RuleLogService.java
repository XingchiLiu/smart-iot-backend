package com.example.iot.service.RuleService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.iot.domain.rule.RuleLog;
import com.example.iot.repository.rule.RuleLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Karson
 * @CreateDate 2020/6/19 15:45
 */
@Service
public class RuleLogService {

    @Autowired
    RuleLogRepository ruleLogRepository;

    public List<RuleLog> getRuleLogByRuleId(int ruleId){
        return ruleLogRepository.getAllByRuleIdOrderByCreateTimeDesc(ruleId);
    }

    public void saveSuccessRuleLog(int ruleId, JSONObject data){
        saveRuleLog(ruleId, data, true);
    }

    public void saveFailureRuleLog(int ruleId, JSONObject data){
        saveRuleLog(ruleId, data, false);
    }

    private void saveRuleLog(int ruleId, JSONObject data, boolean success){
        RuleLog ruleLog = new RuleLog();

        ruleLog.setRuleId(ruleId);
        ruleLog.setData(JSON.toJSONString(data));
        ruleLog.setSuccess(success);

        ruleLogRepository.save(ruleLog);
    }
}
