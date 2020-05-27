package com.example.iot.controller.VO;

import lombok.Data;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/25
 * @description:创建设备模板的格式
 */
@Data
public class TemplateForm {

    private String name;
    private String connectionType;
    private String description;
}
