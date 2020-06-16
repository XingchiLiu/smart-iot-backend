package com.example.iot.controller;

import com.example.iot.controller.VO.DeviceForm;
import com.example.iot.controller.VO.DeviceVO;
import com.example.iot.controller.VO.ResultVO;
import com.example.iot.service.DeviceService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/24
 * @description:
 */
@Api
@Controller
@ResponseBody
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @PostMapping("/add")
    public ResultVO<Integer> add(@RequestBody DeviceForm deviceForm) {
        return deviceService.addDevice(deviceForm);
    }

    @GetMapping("/delete/{id}")
    public ResultVO delete(@PathVariable int id) {
        deviceService.deleteDevice(id);
        return ResultVO.getSuccess("删除成功");
    }

    /*
    修改设备，但是只允许修改设备的名称和描述
     */
    @PostMapping("/update/{id}")
    public ResultVO update(@PathVariable int id, DeviceForm deviceForm) {
        return deviceService.updateDevice(id, deviceForm);
    }

    @GetMapping("/get/{id}")
    public ResultVO<DeviceVO> get(@PathVariable int id) {
        return deviceService.getDeviceById(id);
    }

    @GetMapping("/list")
    public ResultVO<Page<DeviceVO>> list(@RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return deviceService.getDeviceList(page, pageSize);
    }

    @GetMapping("/search")
    public ResultVO<List<DeviceVO>> searchByName(@RequestParam("name") String name) {
        return deviceService.getDeviceListByName(name);
    }


}
