package com.example.iot.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.iot.IotApplicationTests;
import com.example.iot.domain.rule.Rule;
import com.example.iot.service.RuleService.RuleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * @author Karson
 * @CreateDate 2020/6/9 22:00
 */
class RuleServiceTest extends IotApplicationTests {

    @Autowired
    RuleService ruleService;

    @Autowired
    ChannelService channelService;

    ArrayList<Rule> rules;

    @Test
    void filterAndExecute() {
        JSONObject data = new JSONObject();
        JSONArray messages = new JSONArray();
        JSONObject string = new JSONObject();
        string.put("string", 70);
        messages.add(string);
        data.put("deviceId", 10);
        data.put("messages", messages);
        ruleService.filterAndExecute(data);
    }
}