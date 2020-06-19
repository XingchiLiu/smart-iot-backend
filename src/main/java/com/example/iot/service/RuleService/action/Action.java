package com.example.iot.service.RuleService.action;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Karson
 * @CreateDate 2020/6/10 0:35
 */
public abstract class Action {
    protected String target;
    protected JSONObject data;

    public Action(String target, JSONObject data) {
        this.target = target;
        this.data = data;
    }

    public abstract void execute() throws Exception;
}
