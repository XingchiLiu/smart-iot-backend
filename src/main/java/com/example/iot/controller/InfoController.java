package com.example.iot.controller;

import com.example.iot.service.ConnectionService.connection.connectionImpl.MQTTConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/20
 * @description:测试网络功能
 */
@Controller
public class InfoController {
    @Autowired
    MQTTConnectionService mqttConnectionService;

    @GetMapping("/")
    public String index(){
        mqttConnectionService.getConnection();
        return "redirect:/swagger-ui.html";
    }

    @GetMapping("/info")
    public String info(){
        return "info";
    }

    @GetMapping("/api")
    public String api(){
        return "redirect:/swagger-ui.html";
    }

}
