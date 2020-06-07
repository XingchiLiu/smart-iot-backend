package com.example.iot.domain;

import com.example.iot.util.RuleType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Karson
 * @CreateDate 2020/6/7 18:05
 */
@Entity
@Data
public class Rule {
    //规则id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false)
    private int id;
    //设备id
    @Column(nullable=false)
    private int deviceId;
    //规则名
    @Column(nullable=false, length=16)
    private String name;
    //规则描述，非必须。
    @Column(length=100)
    private String description;
    //创建时间
    @Column(nullable=false)
    private Date createTime;
    //最后更新时间
    private Date updateTime;
    //规则类型
    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=4)
    private RuleType ruleType;
    //字段名
    @Column(nullable=false, length=20)
    private String fieldName;
    //阈值
    @Column(nullable=false)
    private double thresholdVal;
}
