package com.example.iot.controller;

import com.example.iot.controller.VO.DeviceForm;
import com.example.iot.controller.VO.ResultVO;
import com.example.iot.controller.VO.RuleForm;
import com.example.iot.repository.RuleRepository;
import com.example.iot.service.RuleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Karson
 * @CreateDate 2020/6/7 18:02
 */
@Api(value = "规则引擎")
@Controller
@RequestMapping("/rule")
@ResponseBody
public class RuleController {
    @Autowired
    RuleService ruleService;

    @GetMapping("/get/{deviceId}")
    public ResultVO<Integer> get(@PathVariable int deviceId){
        return ResultVO.getSuccess("成功",ruleService.getAllByDeviceId(deviceId));
    }

    @PostMapping("/add")
    public ResultVO add(@RequestBody RuleForm ruleForm){
        int id;
        try {
            id = ruleService.addRule(ruleForm);
            return ResultVO.getSuccess("添加成功",id);
        } catch (Exception e){
            return ResultVO.getFailed("添加失败", e);
        }
    }
}
