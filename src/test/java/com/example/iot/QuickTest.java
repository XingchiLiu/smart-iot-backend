package com.example.iot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.iot.util.FileUtil;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/16
 * @description:
 */
public class QuickTest {
    public static void main(String[] args) {
        String TSLTemplate = FileUtil.readTxt("src/main/resources/file/tsl_template.txt");
        System.out.println(TSLTemplate);
    }
}
