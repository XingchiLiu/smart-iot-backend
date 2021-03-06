package com.example.iot.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class DeviceChannel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 数据通道的类型 0：向上 1：向下
    private int channelType;

    // 此通道所属的设备id
    private int deviceId;

    //此通道所属的模板通道id，-1说明没有模板通道
    private int templateChannelId;

    // 用来表示的通道名字
    private String channelName;
}
