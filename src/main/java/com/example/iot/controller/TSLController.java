package com.example.iot.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.iot.controller.VO.ResultVO;
import com.example.iot.domain.thing.TSL;
import com.example.iot.service.ThingService.PropertyService;
import com.example.iot.service.ThingService.TSLService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/15
 * @description: 物模型的controller
 */
@Api
@Controller
@ResponseBody
@RequestMapping("/tsl")
public class TSLController {

    @Autowired
    TSLService tslService;

    /**
     * 根据模板id获得物模型
     * @param templateId
     * @return
     */
    @GetMapping("/{templateId}/get")
    public ResultVO<TSL> getTSL(@PathVariable(name = "templateId")int templateId){
        return ResultVO.getSuccess(tslService.getTSLByTemplateId(templateId));
    }

    /**
     * 根据模板id更新物模型
     * 返回更新后的物模型
     * @param templateId
     * @param content 这是类TSL的content变量
     * @return
     */
    @PostMapping("/{templateId}/update")
    public ResultVO<TSL> setTSL(@PathVariable(name = "templateId")int templateId, @RequestBody JSONObject content){
        return ResultVO.getSuccess(tslService.updateTSL(templateId, content));
    }
}
