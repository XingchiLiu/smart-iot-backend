package com.example.iot.controller;

import com.alibaba.fastjson.JSONArray;
import com.example.iot.controller.VO.ResultVO;
import com.example.iot.domain.thing.Event;
import com.example.iot.domain.thing.Property;
import com.example.iot.service.ThingService.EventService;
import com.example.iot.service.ThingService.PropertyService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/19
 * @description:
 */
@Api
@Controller
@ResponseBody
@RequestMapping("/thing")
public class ThingController {

    @Autowired
    PropertyService propertyService;
    @Autowired
    EventService eventService;

    @GetMapping("/property/list")
    public ResultVO<JSONArray> getPropertyList(@RequestParam("deviceId") int deviceId) {
        return ResultVO.getSuccess(propertyService.getPropertyList(deviceId));
    }

    @GetMapping("/property/history")
    public ResultVO<List<Property>> getPropertyHistory(@RequestParam("deviceId") int deviceId,
                                                       @RequestParam("identifier") String identifier) {
        return ResultVO.getSuccess(propertyService.getPropertyHistory(deviceId, identifier));
    }

    @GetMapping("/event/history")
    public ResultVO<List<Event>> getEventHistory(@RequestParam("deviceId") int deviceId){
        return ResultVO.getSuccess(eventService.getEventHistory(deviceId));
    }

}
