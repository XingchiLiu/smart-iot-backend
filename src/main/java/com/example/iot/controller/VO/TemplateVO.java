package com.example.iot.controller.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/27
 * @description:
 */
@Data
public class TemplateVO {
    private int id;
    //模板名字
    private String name;
    //产品品类。目前为默认值common
    private String classification;
    /*
    连接方式，现在有MQTT和HTTP
     */
    private String connectionType;
    //数据格式，目前为默认值ICA_Format
    private String dataFormatType;
    //描述
    private String description;

    //创建时间，系统自动修改
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    //更新时间，系统自动修改
    private Date updateTime;
}
