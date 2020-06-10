package com.example.iot.service;

import com.alibaba.fastjson.JSONObject;
import com.example.iot.IotApplicationTests;
import com.example.iot.domain.Rule;
import com.example.iot.repository.RuleRepository;
import com.example.iot.service.RuleService.RuleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

/**
 * @author Karson
 * @CreateDate 2020/6/9 22:00
 */
class RuleServiceTest extends IotApplicationTests {

    @Autowired
    RuleService ruleService;

    RuleRepository ruleRepository = Mockito.mock(RuleRepository.class);

    ArrayList<Rule> rules;

    @Test
    void filterAndExecute() {
        when(ruleRepository.getAllByDeviceId(10)).thenReturn(rules);

        JSONObject data = new JSONObject();
        data.put("deviceId", 10);
        data.put("string", 70);
        ruleService.filterAndExecute(data);
    }
}