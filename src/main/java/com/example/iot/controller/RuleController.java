package com.example.iot.controller;

import com.example.iot.controller.VO.ResultVO;
import com.example.iot.controller.VO.RuleForm;
import com.example.iot.service.RuleService.RuleLogService;
import com.example.iot.service.RuleService.RuleService;
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
    @Autowired
    RuleLogService ruleLogService;

    @GetMapping("/log/{ruleId}")
    public ResultVO getRuleLog(@PathVariable int ruleId){
        return ResultVO.getSuccess("成功", ruleLogService.getRuleLogByRuleId(ruleId));
    }

    @GetMapping("/get/{deviceId}")
    public ResultVO get(@PathVariable int deviceId) {
        return ResultVO.getSuccess("成功", ruleService.getAllByDeviceId(deviceId));
    }

    @GetMapping("/get/all")
    public ResultVO get() {
        return ResultVO.getSuccess("成功", ruleService.getAll());
    }

    @PostMapping("/add")
    public ResultVO add(@RequestBody RuleForm ruleForm) {
        int id;
        try {
            id = ruleService.addRule(ruleForm);
            return ResultVO.getSuccess("添加成功", id);
        } catch (Exception e) {
            return ResultVO.getFailed("添加失败", e);
        }
    }

    @PostMapping("/modify/{ruleId}")
    public ResultVO modify(@PathVariable int ruleId, @RequestBody RuleForm ruleForm) {
        if (ruleId != ruleForm.getId()) {
            return ResultVO.getFailed("请求出错，规则ID不符。");
        }
        try {
            int id = ruleService.updateRule(ruleForm);
            return ResultVO.getSuccess("更新成功", id);
        } catch (Exception e) {
            return ResultVO.getFailed("更新失败", e);
        }
    }

    @DeleteMapping("/delete/{ruleId}")
    public ResultVO delete(@PathVariable int ruleId) {
        try {
            ruleService.deleteRule(ruleId);
            return ResultVO.getSuccess("删除成功");
        } catch (Exception e) {
            return ResultVO.getFailed("删除失败");
        }
    }
}
