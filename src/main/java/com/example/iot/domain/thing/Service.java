package com.example.iot.domain.thing;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/16
 * @description:
 */
@Document(collection = "thing_service")
@Data
public class Service {
    @Id
    private String id;
    private int deviceId;
    private Date time;
    /*
    functionInfo
    {
    		"timestamp":"",
            "identifier": "服务唯一标识符（产品下唯一，其中set/get是根据属性的accessMode默认生成的服务。）",
    		"serviceKey": "UUID，用于标识同一次服务",
            "name": "服务名称",
            "desc": "服务描述",
    		"category": "request",
            "callType": "async（异步调用）或sync（同步调用）",
            "inputData": [
            ],
        }
     */
    private JSONObject functionInfo;
    private JSONObject data;
}
