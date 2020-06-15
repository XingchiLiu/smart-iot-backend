package com.example.iot.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

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

    public Map<String, Object> getDataMap() {
        return data.stream()
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
