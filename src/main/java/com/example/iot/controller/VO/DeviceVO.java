package com.example.iot.controller.VO;

import com.example.iot.domain.Device;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/10
 * @description:很多和Device类似，高耦合
 */
@Data
public class DeviceVO extends Device {

    /**
     * 这些是从Device复制来的信息
     */
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 下面是比类Device多增加的部分
     */
    private String templateName;
    //最后联络时间，值设备最后一次上报数据的时间
    private Date lastContactTime;
}
