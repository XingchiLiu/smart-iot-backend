package com.example.iot.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/1
 * @description: 大坑！！！group是mysql关键字，表名字如果是group会失败
 */
@Entity
@Data
public class DeviceGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //组名，如果是产品组那么组名为产品名
    private String name;
    /*
    product或者self_defined
    mysql保留字
     */
    private String type;
    //描述，如果是产品组那么描述是产品的描述
    private String description;

    //如果是产品组，那么是产品id，不然为-1
    private int templateId;

}
