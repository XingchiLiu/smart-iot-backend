package com.example.iot.controller;

import com.alibaba.fastjson.JSON;
import com.example.iot.controller.VO.MessageForm;
import com.example.iot.controller.VO.ResultVO;
import com.example.iot.service.ConnectionService.MqttService;
import com.example.iot.service.ConnectionService.HttpService;
import com.example.iot.service.InMessageService;
import com.example.iot.service.OutMessageService;
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
    InMessageService inMessageService;
    @Autowired
    OutMessageService outMessageService;

    @PostMapping("/send-to-platform")
    public ResultVO<Integer> sendMessageToPlatform(@RequestBody MessageForm messageForm){
        try {
            String message = JSON.toJSONString(messageForm);
            inMessageService.addPub(messageForm.getTopic(), 0, message);
        }catch (Exception e){
            ResultVO.getFailed("发送失败");
        }
        return ResultVO.getSuccess("发送成功");
    }

    @PostMapping("/send-to-device")
    public ResultVO<Integer> sendMessageToDevice(@RequestBody MessageForm messageForm, int deviceId){
        if(messageForm.getDeviceId() != deviceId){
            return ResultVO.getFailed("id不一致！");
        }

        try{
            outMessageService.sendMessageToDevice(deviceId,messageForm);
            return ResultVO.getSuccess("发送数据给设备成功！");
        }catch (Exception e){
            return ResultVO.getFailed("发送数据给设备失败！");
        }
    }
}
