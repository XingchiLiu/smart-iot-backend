package com.example.iot.domain.rule;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Karson
 * @CreateDate 2020/6/19 15:51
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class RuleLog {
    //日志id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;
    //规则id
    @Column(nullable = false)
    private int ruleId;
    //数据
    @Column(nullable = false)
    private String data;
    //成功与否
    @Column(nullable = false)
    private boolean success;
    //创建时间
    @Column(updatable = false, nullable = false)
    @CreatedDate
    private Date createTime;
}
