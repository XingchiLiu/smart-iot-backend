package com.example.iot.service.RuleService;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Karson
 * @CreateDate 2020/6/10 0:45
 */
public class LinkageAction extends Action {
    public LinkageAction(String target, JSONObject data){
        super(target, data);
    }

    @Override
    public void execute() {
        //TODO:调用数据通道向设备发送数据。
        String[] array = target.split(":", 1);
        int deviceId = Integer.parseInt(array[0]);
        String message = array[1];
    }
}
