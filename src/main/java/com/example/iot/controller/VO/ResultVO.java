package com.example.iot.controller.VO;

import lombok.Builder;
import lombok.Data;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/5/20
 * @description:
 */
@Data
@Builder
public class ResultVO {

    public static int SUCCESS = 0;
    public static int GENERAL_FAILED = 1;
    public static int AFFILIATION_NOT_EXIST = 34;

    /*
    0代表成功，1代表失败
     */
    int result;
    //内容
    Object data;
    /*
    返回的信息
     */
    String message;
}
