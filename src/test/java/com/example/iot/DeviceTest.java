package com.example.iot;

import com.example.iot.controller.VO.DeviceForm;
import com.example.iot.repository.DeviceRepository;
import com.example.iot.service.DeviceService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/2
 * @description:
 */
public class DeviceTest extends IotApplicationTests{

    @Autowired
    DeviceService deviceService;
    @Autowired
    DeviceRepository deviceRepository;

    @Test
    public void test1(){
        DeviceForm deviceForm = new DeviceForm();
        deviceForm.setName("test02");
        deviceForm.setTemplateId(8);
        deviceService.addDevice(deviceForm);
    }
}
