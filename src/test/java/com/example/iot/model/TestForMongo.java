package com.example.iot.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/21
 * @description:测试mongodb
 */
@Document(collection = "test")
@Data
public class TestForMongo {
    private String name;
    private String info;
}
