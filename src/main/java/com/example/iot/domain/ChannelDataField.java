package com.example.iot.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class ChannelDataField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 此项数据的名称
    private String fieldName;

    // 此项数据的类型：0 boolean 1 double 2 string 3 int
    private int fieldType;

    // 此项数据项对应的通道id
    private int channelId;

    // 决定了channelId的类型：0 templateChannelId 1 deviceChannelId
    private int channelType;

}
