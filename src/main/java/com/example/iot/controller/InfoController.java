package com.example.iot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/20
 * @description:测试网络功能
 */
@Controller
public class InfoController {

    @GetMapping("/")
    public String index(){
        return "info";
    }

    @GetMapping("/info")
    public String info(){
        return "info";
    }

}
