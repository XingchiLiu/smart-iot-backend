package com.example.iot.service.RuleService;

import com.alibaba.fastjson.JSONObject;
import com.example.iot.util.RuleActionType;

/**
 * @author Karson
 * @CreateDate 2020/6/10 0:33
 */
public class ActionFactory {

    public static Action getAction(RuleActionType ruleActionType, String target, JSONObject data) throws Exception {
        switch (ruleActionType) {
            case FORWARD:
                return new ForwardAction(target, data);
            case LINKAGE:
                return new LinkageAction(target, data);
        }
        throw new Exception("Unknown Action Type.");
    }
}
