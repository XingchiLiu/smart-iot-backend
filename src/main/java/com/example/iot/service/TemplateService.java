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
        return result.getId();
    }

}
