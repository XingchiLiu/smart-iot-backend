package com.example.iot.service;

import com.example.iot.controller.VO.TemplateForm;
import com.example.iot.domain.DeviceTemplate;
import com.example.iot.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/24
 * @description:设备模板
 *
 */
@Service
public class TemplateService {

    @Autowired
    TemplateRepository templateRepository;
    @Autowired
    GroupService groupService;
    @Autowired
    ChannelService channelService;
    @Autowired
    ChannelDataFieldService channelDataFieldService;


    public int addTemplate(TemplateForm templateForm){
        DeviceTemplate deviceTemplate = new DeviceTemplate();
        //复制信息
        deviceTemplate.setName(templateForm.getName());
        deviceTemplate.setConnectionType(templateForm.getConnectionType());
        deviceTemplate.setDescription(templateForm.getDescription());
        //填充默认信息
        deviceTemplate.setClassification("common");
        deviceTemplate.setDataFormatType("ICA_Format");
        deviceTemplate.setCreateTime(new Date());
        deviceTemplate.setUpdateTime(deviceTemplate.getCreateTime());

        DeviceTemplate result = templateRepository.save(deviceTemplate);
        //创建产品组
        groupService.createProductGroup(result);

        //如果设置了数据通道
        if(templateForm.getChannelType() != -1) {
            //获得设备模板id
            int templateId = result.getId();

            // 创建数据通道
            int channelId = channelService.addTemplateChannel(templateForm.getChannelType(), templateId);

            // 创建数据通道里的字段
            if(templateForm.getChannelData() != null && !templateForm.getChannelData().isEmpty()) {
                channelDataFieldService.addDataFields(templateForm.getChannelData(), channelId, 0);
            }
        }

        return result.getId();
    }

}
