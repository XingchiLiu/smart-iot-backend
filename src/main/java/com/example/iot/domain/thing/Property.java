package com.example.iot.domain.thing;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;
    /*
    functionInfo
    {
            "identifier": "属性唯一标识符（产品下唯一）",
            "name": "属性名称",
    		"desc": "描述",
            "accessMode": "属性读写类型：只读（r）或读写（rw）。",
            "dataType": {
                "type": "属性类型: int（原生）、double（原生）、string（原生）、struct（结构体类型，可包含前面3种类型）",
                "specs": {
                    "min": "参数最小值（int、double类型特有）",
                    "max": "参数最大值（int、double类型特有）",
                    "unitName": "单位名称（int、double类型特有）",
                    "maxLength": "字符串大小（string型特有）"
                }
            },
    }
     */
    private JSONObject functionInfo;
    private JSONObject data;
}
