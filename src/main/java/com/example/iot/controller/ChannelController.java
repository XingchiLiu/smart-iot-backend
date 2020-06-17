package com.example.iot.controller;

import com.example.iot.controller.VO.ChannelDataFieldForm;
import com.example.iot.controller.VO.DeviceChannelForm;
import com.example.iot.controller.VO.ResultVO;
import com.example.iot.controller.VO.TemplateChannelForm;
import com.example.iot.domain.ChannelDataField;
import com.example.iot.domain.DeviceChannel;
import com.example.iot.domain.TemplateChannel;
import com.example.iot.service.ChannelDataFieldService;
import com.example.iot.service.ChannelService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "数据通道")
@Controller
@ResponseBody
@RequestMapping("/channel")
public class ChannelController {
    @Autowired
    ChannelDataFieldService channelDataFieldService;
    @Autowired
    ChannelService channelService;


    @PostMapping("/add/field")
    public ResultVO<Integer> addDataField(@RequestBody ChannelDataFieldForm channelDataFieldForm) {
        try {
            int id = channelDataFieldService.addDataField(channelDataFieldForm);
            return ResultVO.getSuccess("数据添加成功", id);
        } catch (Exception e) {
            return ResultVO.getFailed("数据添加失败", e);
        }
    }

    @PostMapping("/add/template-channel")
    public ResultVO<Integer> addTemplateChannel(@RequestBody TemplateChannelForm templateChannelForm) {
        try {
            int templateChannelId = channelService.addTemplateChannel(templateChannelForm);
            return ResultVO.getSuccess("模板数据通道创建成功", templateChannelId);
        } catch (Exception e) {
            return ResultVO.getFailed("模板数据通道创建失败", e);
        }
    }

    @PostMapping("/add/device-channel")
    public ResultVO<Integer> addDeviceChannel(@RequestBody DeviceChannelForm deviceChannelForm) {
        try {
            int deviceChannelId = channelService.addDeviceChannel(deviceChannelForm);
            return ResultVO.getSuccess("设备数据通道创建成功", deviceChannelId);
        } catch (Exception e) {
            return ResultVO.getFailed("设备数据通道创建失败", e);
        }
    }

    @GetMapping("/list/template-channel")
    public List<TemplateChannel> getAllTemplateChannels() {
        return channelService.getAllTemplateChannels();
    }

    @GetMapping("/get/template-channel/{templateId}")
    public List<TemplateChannel> getTemplateChannelsByTemplateId(@PathVariable int templateId){
        return channelService.getTemplateChannelsByTemplateId(templateId);
    }

    @GetMapping("/get/device-channel/{deviceId}")
    public List<DeviceChannel> getDeviceChannelByDeviceId(@PathVariable int deviceId){
        return channelService.getDeviceChanelByDeviceId(deviceId);
    }

    @GetMapping("/get/data")
    public List<ChannelDataField> getChannelDataFieldsByChannelTypeAndChannelId
            (@RequestParam int channelType, @RequestParam int channelId){
        return channelDataFieldService.getDataFieldsByChannelIdAndChannelType(channelId, channelType);
    }

    @GetMapping("/list/device-channel")
    public List<DeviceChannel> getAllDeviceChannels() {
        return channelService.getAllDeviceChannels();
    }

    // 可以修改 fieldName
    @PostMapping("/modify/data/{dataFieldId}")
    public ResultVO<Integer> modifyDataField(@PathVariable int dataFieldId, @RequestBody ChannelDataFieldForm channelDataFieldForm) {
        if (dataFieldId != channelDataFieldForm.getId()) {
            return ResultVO.getFailed("id不一致！");
        }
        try {
            int id = channelDataFieldService.updateDataField(channelDataFieldForm);
            return ResultVO.getSuccess("数据修改成功", id);
        } catch (Exception e) {
            return ResultVO.getFailed("数据修改失败", e);
        }
    }

    // 可以修改 channelName
    @PostMapping("/modify/template-chanel/{templateChannelId}")
    public ResultVO<Integer> modifyNameOfTemplateChannel(@PathVariable int templateChannelId,
                                                         @RequestBody TemplateChannelForm templateChannelForm) {
        if (templateChannelId != templateChannelForm.getId()) {
            return ResultVO.getFailed("id不一致！");
        }
        try {
            int id = channelService.updateTemplateChannel(templateChannelForm);
            return ResultVO.getSuccess("模板数据通道修改成功", id);
        } catch (Exception e) {
            return ResultVO.getFailed("模板数据通道修改失败", e);
        }
    }

    // 可以修改 channelName
    @PostMapping("/modify/device-channel/{deviceChannelId}")
    public ResultVO<Integer> modifyNameOfDeviceChannel(@PathVariable int deviceChannelId,
                                                       @RequestBody DeviceChannelForm deviceChannelForm) {

        if (deviceChannelId != deviceChannelForm.getId()) {
            return ResultVO.getFailed("id不一致！");
        }
        try {
            int id = channelService.updateDeviceChannel(deviceChannelForm);
            return ResultVO.getSuccess("模板数据通道修改成功", id);
        } catch (Exception e) {
            return ResultVO.getFailed("模板数据通道修改失败", e);
        }
    }

    @PostMapping("/delete/data/{dataFieldId}")
    public ResultVO<Integer> deleteDataField(@PathVariable int dataFieldId) {
        try {
            channelDataFieldService.deleteDataField(dataFieldId);
            return ResultVO.getSuccess("删除成功");
        } catch (Exception e) {
            return ResultVO.getFailed("删除失败");
        }
    }

    @PostMapping("/delete/template-channel/{templateChannelId}")
    public ResultVO<Integer> deleteTemplateChannel(@PathVariable int templateChannelId) {
        try {
            channelService.deleteTemplateChannel(templateChannelId);
            return ResultVO.getSuccess("删除成功");
        } catch (Exception e) {
            return ResultVO.getFailed("删除失败");
        }
    }

    @PostMapping("/delete/device-channel/{deviceChannelId}")
    public ResultVO<Integer> deleteDeviceChannel(@PathVariable int deviceChannelId) {
        try {
            channelService.deleteDeviceChannel(deviceChannelId);
            return ResultVO.getSuccess("删除成功");
        } catch (Exception e) {
            return ResultVO.getFailed("删除失败");
        }
    }

}
