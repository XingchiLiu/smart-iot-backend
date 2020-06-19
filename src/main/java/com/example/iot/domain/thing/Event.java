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
@Document(collection = "thing_event")
@Data
public class Event {
    @Id
    private String id;
    private int deviceId;
    private Date time;
    /*
    {
    		"timestamp":"",
            "identifier": "事件唯一标识符（产品下唯一，其中post是默认生成的属性上报事件。）",
            "name": "事件名称",
            "desc": "事件描述",
            "type": "事件类型（info、alert、error）",
            "outputData": [
                {
            		"identifier":"",
            		"name":"",
            		"data":""
        		},
        		{}
            ],
        }
     */
    private JSONObject content;
}
