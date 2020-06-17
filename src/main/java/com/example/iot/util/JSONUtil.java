package com.example.iot.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/16
 * @description:
 */
public class JSONUtil {

    public static String jsonFormat(String target){
        JSONObject jsonObject = JSONObject.parseObject("{\n" +
                "    \"schema\": \"http://101.37.80.37:8081/tsl\",\n" +
                "    \"templateId\":\"\",\n}");
        String pretty = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);

        System.out.println(pretty);
        return pretty;
    }
}
