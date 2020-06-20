package com.example.iot.thing;

import com.alibaba.fastjson.JSONObject;
import com.example.iot.IotApplicationTests;
import com.example.iot.domain.thing.Property;
import com.example.iot.service.ThingService.PropertyService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Date;

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
    @Ignore
    @Test
    public void test1() {
        /*
        空调设备，造数据，26度左右的温度随机数，
        小于26就1低功率，大于26就高功率。
         */
        long aWeekAgo = new Date().getTime() - 7*24*60*60*1000;
        for (int i = 0; i < 100; i++) {
            Property property_t = new Property();
            property_t.setDeviceId(15);
            Property property_r = new Property();
            property_r.setDeviceId(15);
//            过去了1min
            Date date = new Date(aWeekAgo + i*60*1000);
            double temperature = 24 + 4*Math.random();
            int running_status = 0;
            if(temperature>=26){
                running_status = 2;
            }
            else {
                running_status = 1;
            }
            property_t.setFunctionInfo(getTemperatureFormat());
            property_t.setData(String.valueOf(temperature));
            property_t.setTime(date);
            property_r.setFunctionInfo(getRunningModeFormat());
            property_r.setData(String.valueOf(running_status));
            property_r.setTime(date);

            mongoTemplate.save(property_r);
            mongoTemplate.save(property_t);
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
