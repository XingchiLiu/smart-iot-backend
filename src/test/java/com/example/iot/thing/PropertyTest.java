package com.example.iot.thing;

import com.alibaba.fastjson.JSONObject;
import com.example.iot.IotApplicationTests;
import com.example.iot.domain.thing.Property;
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
    测试，造一些空调的数据
     */
    @Test
    public void test1() {
        Property testProperty = new Property();
        testProperty.setDeviceId(14);
        for (int i = 0; i < 100; i++) {

        }
    }


    private JSONObject getTemperatureFormat(){
        return JSONObject.parseObject("{\n" +
                "\t\t\t\"identifier\": \"temperature\",\n" +
                "\t\t\t\"name\": \"温度\",\n" +
                "\t\t\t\"desc\": \"来自于温度传感器1号的数据\",\n" +
                "\t\t\t\"accessMode\": \"r\",\n" +
                "\t\t\t\"dataType\": {\n" +
                "\t\t\t\t\"type\": \"double\",\n" +
                "\t\t\t\t\"specs\": {\n" +
                "\t\t\t\t\t\"min\": \"0\",\n" +
                "\t\t\t\t\t\"max\": \"50\",\n" +
                "\t\t\t\t\t\"unitName\": \"摄氏度/°C\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}");
    }

    private JSONObject getRunningModeFormat(){
        return JSONObject.parseObject("{\n" +
                "\t\t\t\"identifier\": \"running_mode\",\n" +
                "\t\t\t\"name\": \"运行模式\",\n" +
                "\t\t\t\"desc\": \"空调运行模式。1代表低功率；2代表高功率。\",\n" +
                "\t\t\t\"accessMode\": \"rw\",\n" +
                "\t\t\t\"dataType\": {\n" +
                "\t\t\t\t\"type\": \"int\",\n" +
                "\t\t\t\t\"specs\": {\n" +
                "\t\t\t\t\t\"min\": \"1\",\n" +
                "\t\t\t\t\t\"max\": \"2\",\n" +
                "\t\t\t\t\t\"unitName\": \"\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}");
    }

}
