package com.example.iot.service;

import com.example.iot.controller.VO.DeviceForm;
import com.example.iot.controller.VO.ResultVO;
import com.example.iot.domain.Device;
import com.example.iot.domain.DeviceTemplate;
import com.example.iot.repository.DeviceRepository;
import com.example.iot.repository.TemplateRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/24
 * @description:
 */
@Service
public class DeviceService {

    @Autowired
    TemplateRepository templateRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    ChannelService channelService;

    public ResultVO<Integer> addDevice(DeviceForm deviceForm){
        int templateId = deviceForm.getTemplateId();
        Optional<DeviceTemplate> deviceTemplateOptional = templateRepository.findById(templateId);
        if(!deviceTemplateOptional.isPresent()){
            return ResultVO.getFailed("模板不存在");
        }
        DeviceTemplate deviceTemplate = deviceTemplateOptional.get();
        Device device = new Device();
        BeanUtils.copyProperties(deviceTemplate, device,"id", "name", "description");
        //如果没填名字，设置名字
        if(StringUtils.isEmpty(deviceForm.getName())){
            long time = System.currentTimeMillis();
            String name = deviceTemplate.getName()+"_device" + (int) time % 1000;
            device.setName(name);
        }
        else {
            device.setName(deviceForm.getName());
        }
        device.setDescription(deviceForm.getDescription());
        device.setTemplateId(deviceTemplate.getId());
        device.setCreateTime(new Date());
        device.setUpdateTime(device.getCreateTime());
        //默认为管理员1
        device.setUserId(1);

        /*
        复制数据通道、物模型、影子设备
         */

        Device result = deviceRepository.save(device);

        channelService.addChannelsToNewlyAddedDevice(result.getId(), templateId);

        return ResultVO.getSuccess("创建成功", result.getId());
    }
}
