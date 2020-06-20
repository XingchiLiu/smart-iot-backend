package com.example.iot.thing;

import com.alibaba.fastjson.JSONObject;
import com.example.iot.IotApplicationTests;
import com.example.iot.domain.thing.Event;
import com.example.iot.domain.thing.Property;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Date;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/19
 * @description:
 */
public class EventTest extends IotApplicationTests {

    @Autowired
    MongoTemplate mongoTemplate;

    /*
    插入数据，高温警报
     */
    @Ignore
    @Test
    public void test1(){
        /*
        高温警报，温度高于40度就报警
         */
        long aWeekAgo = new Date().getTime() - 7*24*60*60*1000;
        for (int i = 0; i < 20; i++) {
            Event event = new Event();
            event.setDeviceId(15);
//            过去了1小时
            Date date = new Date(aWeekAgo + i*60*60*1000);
            double temperature = 40 + Math.random();
            event.setTime(date);
            event.setFunctionInfo(getHeatEventFormat());
            event.setData(temperature +"°C");

            mongoTemplate.save(event);
        }
    }

    private JSONObject getHeatEventFormat(){
        return JSONObject.parseObject("{\n" +
                "      \"outputData\": [\n" +
                "        {\n" +
                "          \"identifier\": \"temperature\",\n" +
                "          \"name\": \"温度\",\n" +
                "          \"desc\": \"来自于温度传感器1号的数据\",\n" +
                "          \"accessMode\": \"r\",\n" +
                "          \"dataType\": {\n" +
                "            \"type\": \"double\",\n" +
                "            \"specs\": {\n" +
                "              \"min\": \"0\",\n" +
                "              \"max\": \"50\",\n" +
                "              \"unitName\": \"摄氏度/°C\"\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      ],\n" +
                "      \"identifier\": \"heat_warning\",\n" +
                "      \"name\": \"高温警报\",\n" +
                "      \"type\": \"alert\",\n" +
                "      \"desc\": \"高温警报，当温度大于等于40度的时候触发。上报数据为温度。\"\n" +
                "    }");
    }
}
