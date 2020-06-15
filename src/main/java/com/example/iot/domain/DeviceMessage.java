package com.example.iot.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(collection = "device_message")
@Data
public class DeviceMessage implements Serializable {

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

}
