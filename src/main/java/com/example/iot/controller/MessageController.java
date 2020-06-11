package com.example.iot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.iot.controller.VO.MessageForm;
import com.example.iot.controller.VO.ResultVO;
import com.example.iot.service.ConnectionService.connection.connectionImpl.MQTTConnectionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value="数据发送")
@Controller
@ResponseBody
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MQTTConnectionService mqttConnectionService;

    @PostMapping("/send")
    public ResultVO<Integer> sendMessage(@RequestBody MessageForm messageForm){
        try {
            String message = JSON.toJSONString(messageForm);
            mqttConnectionService.addPub(messageForm.getTopic(), 0, message);
        }catch (Exception e){
            ResultVO.getFailed("发送失败");
        }
        return ResultVO.getSuccess("发送成功");
    }
}
