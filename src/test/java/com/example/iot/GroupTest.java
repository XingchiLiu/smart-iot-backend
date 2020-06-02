package com.example.iot;

import com.example.iot.domain.DeviceGroup;
import com.example.iot.repository.GroupRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/2
 * @description:
 */
public class GroupTest extends IotApplicationTests {

    @Autowired
    GroupRepository groupRepository;

    @Test
    public void test1(){
        DeviceGroup group = new DeviceGroup();
        group.setTemplateId(6);
        group.setName("test");
        group.setType("product");
        group.setDescription("hello");
        groupRepository.save(group);
    }
}
