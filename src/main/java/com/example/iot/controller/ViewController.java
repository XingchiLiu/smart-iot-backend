package com.example.iot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/18
 * @description:
 */
@Controller
public class ViewController {

    @GetMapping("/template")
    public String template(){
        return "template";
    }

    @GetMapping("/template/detail/{id}")
    public String templateDetail(@PathVariable(name = "id") int templateId){
        return "templateDetail";
    }

    @GetMapping("/device")
    public String device(){
        return "device";
    }

    @GetMapping("/device/detail/{id}")
    public String deviceDetail(@PathVariable(name = "id") int deviceId){
        return "deviceDetail";
    }
}
