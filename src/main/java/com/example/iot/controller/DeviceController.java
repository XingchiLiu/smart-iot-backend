package com.example.iot.controller;

import com.example.iot.controller.VO.DeviceForm;
import com.example.iot.controller.VO.ResultVO;
import com.example.iot.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/24
 * @description:
 */
@Controller
@ResponseBody
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @GetMapping("/add")
    public ResultVO<Integer> add(@RequestBody DeviceForm deviceForm){
        return deviceService.addDevice(deviceForm);
    }
}
