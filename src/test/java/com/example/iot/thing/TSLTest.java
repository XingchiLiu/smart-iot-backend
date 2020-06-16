package com.example.iot.thing;

import com.example.iot.IotApplicationTests;
import com.example.iot.service.ThingService.TSLService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/16
 * @description: tsl类测试
 */
public class TSLTest extends IotApplicationTests {

    @Autowired
    TSLService tslService;

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     *查询
     */
    @Test
    public void test1(){
        System.out.println(tslService.getTSLByTemplateId(0));
    }

}
