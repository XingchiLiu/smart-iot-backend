package com.example.iot;

import com.example.iot.controller.VO.TemplateForm;
import com.example.iot.repository.TemplateRepository;
import com.example.iot.service.TemplateService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/2
 * @description:
 */
public class TemplateTest extends IotApplicationTests{

    @Autowired
    TemplateRepository templateRepository;
    @Autowired
    TemplateService templateService;

    @Test
    @Ignore
    //添加数据
    public void test1(){
        TemplateForm templateForm = new TemplateForm();
        templateForm.setName("product_test");
        templateForm.setConnectionType("MQTT");
        templateService.addTemplate(templateForm);
    }

    //查找数据
    @Test
    public void test2(){
        System.out.println(templateRepository.findById(3));
    }
}
