package com.example.iot.domain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.*;

@Document(collection = "device_message")
@Data
public class ReceivedMessage implements Serializable {

    // topic
    private String topic;

    // 设备的id
    private int deviceId;

    // 通道的id
    private int channelId;

    // 发送的时间
    private Date date;

    // 要发送的数据
    private List<Map<String, Object>> data;

    public ReceivedMessage(JSONObject message){
        this.topic = message.getString("topic");
        this.deviceId = message.getIntValue("deviceId");
        this.channelId = message.getIntValue("channelId");
        this.data = (List<Map<String,Object>>) JSONArray.parse(message.getJSONArray("data").toJSONString());
        System.out.println(this.data.get(0));
        this.date = message.getDate("date");
    }

    public ReceivedMessage(){}
}
