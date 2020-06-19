package com.example.iot.thing;

import com.example.iot.IotApplicationTests;
import com.example.iot.service.ThingService.PropertyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/19
 * @description: 物模型-属性测试
 */
public class PropertyTest extends IotApplicationTests {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    PropertyService propertyService;

    /*
    测试，装入数据
     */
    @Test
    public void test1(){

    }
}
