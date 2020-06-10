package com.example.iot.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class TemplateChannel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 数据通道的类型 0：向上 1：向下
    private int channelType;

    // 此通道所属的设备模板id
    private int templateId;

    // 同来标识的通道名
    private String channelName;
}
