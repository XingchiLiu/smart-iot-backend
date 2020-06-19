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
 * @description: 物模型中的属性，存取的是真正的业务数据
 */
@Document(collection = "thing_property")
@Data
public class Property {

    @Id
    private String id;
    private int deviceId;
    private Date time;
    /*
    {
            "identifier":"",
            "name":"",
            "data":""
    }
     */
    private JSONObject content;

}
