package com.example.iot.controller;

import com.example.iot.controller.VO.TemplateForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/24
 * @description:
 */
@Controller
@ResponseBody
@RequestMapping("/template")
public class DeviceTemplateController {

    @PostMapping("/add")
    public int add(@RequestBody TemplateForm templateForm){
        
    }
}
