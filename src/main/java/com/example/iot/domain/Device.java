package com.example.iot.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/22
 * @description:设备类
 */
@Entity
@Data
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //模板id
    private int templateId;
    //创建者id，默认为0，root
    private int userId;
    //注：在同一模板下唯一
    private String name;
    //描述，非必须。区别于模板描述
    private String description;

    //产品品类。目前为默认值common
    private String classification;
    /*
    连接方式，现在有MQTT和HTTP
     */
    private String connectionType;
    //数据格式，目前为默认值ICA_Format
    private String dataFormatType;

    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}
