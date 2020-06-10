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

    /**
     * 创建设备
     */
    @Test
    public void test1(){
        DeviceForm deviceForm = new DeviceForm();
        deviceForm.setName("test02");
        deviceForm.setTemplateId(8);
        System.out.println(deviceService.addDevice(deviceForm));
    }

    /**
     * 分页查询设备
     */
    @Test
    public void test2(){
        System.out.println(deviceService.getDeviceList(0,5).getContent());
    }

    /**
     * 测试按名查询设备
     */
    @Test
    public void test3(){
        System.out.println(deviceService.getDeviceListByName("test"));
    }
}
