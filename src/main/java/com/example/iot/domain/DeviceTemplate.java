package com.example.iot.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/22
 * @description:设备模板
 */
//@Entity
@Data
public class DeviceTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //模板名字
    @Column(unique = true)
    String name;
    //产品品类。目前为默认值common
    String classification;
    /*
    连接方式，现在有MQTT和HTTP
     */
    private String connectionType;
    //数据格式，目前为默认值ICA_Format
    private String dataFormatType;
    //描述
    private String description;

}
