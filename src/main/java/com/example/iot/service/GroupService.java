package com.example.iot.service;

import com.example.iot.domain.DeviceGroup;
import com.example.iot.domain.DeviceTemplate;
import com.example.iot.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/1
 * @description:分组服务
 */
@Service
public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    public void createProductGroup(DeviceTemplate deviceTemplate){
        DeviceGroup group = new DeviceGroup();
        group.setTemplateId(deviceTemplate.getId());
        group.setName(deviceTemplate.getName());
        group.setType("product");
        group.setDescription(deviceTemplate.getDescription());
        groupRepository.save(group);
    }
}
