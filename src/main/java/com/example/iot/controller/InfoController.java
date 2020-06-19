package com.example.iot.controller;

import com.example.iot.domain.thing.TSL;
import com.example.iot.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/20
 * @description:测试网络功能
 */
@Controller
public class InfoController {

    @GetMapping("/")
    public String index() {
        return "redirect:/swagger-ui.html";
    }

    @GetMapping("/info")
    public String info() {
        return "info";
    }

    @GetMapping("/tsl")
    public String tsl(){
        return "tsl";
    }

    @GetMapping("/api")
    public String api() {
        return "redirect:/swagger-ui.html";
    }

    @GetMapping("/shadow")
    @ResponseBody
    public String test(){
        String shadow = FileUtil.readTxt("src/main/resources/file/shadow.txt");
        return shadow;
    }

}
