package com.example.iot.service.RuleService;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Karson
 * @CreateDate 2020/6/10 0:45
 */
public class ForwardAction extends Action {

    public ForwardAction(String target, JSONObject data) {
        super(target, data);
    }

    @Override
    public void execute() {
        //TODO:向数据库转发数据。
    }
}
