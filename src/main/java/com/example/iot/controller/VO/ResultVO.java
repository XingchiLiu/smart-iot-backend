package com.example.iot.controller.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class ResultVO<T> {

    @ApiModelProperty(value = "状态码 0失败 1成功 2未登录 3没有权限")
    private Integer code;

    @ApiModelProperty(value = "返回信息")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public ResultVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 请求成功  状态码 1
     *
     * @param msg 返回信息
     * @param <T> 类型
     * @return ResultVO
     */
    public static <T> ResultVO getSuccess(String msg) {
        return new ResultVO(1, msg);
    }

    public static <T> ResultVO getSuccess(T data){
        return new ResultVO(1,"成功");
    }

    /**
     * 请求成功  状态码 1
     *
     * @param msg  返回信息
     * @param data 返回对象
     * @param <T>  类型
     * @return ResultVO
     */
    public static <T> ResultVO getSuccess(String msg, T data) {
        return new ResultVO(1, msg, data);
    }

    /**
     * 请求失败   状态码 0
     *
     * @param msg 返回信息
     * @param <T> 类型
     * @return ResultVO
     */
    public static <T> ResultVO getFailed(String msg) {
        return new ResultVO(0, msg);
    }

    /**
     * 请求失败  状态 0
     *
     * @param msg  返回信息
     * @param data 返回数据
     * @param <T>  类型
     * @return ResultVO
     */
    public static <T> ResultVO getFailed(String msg, T data) {
        return new ResultVO(0, msg, data);
    }


    /**
     * 用户未登录
     *
     * @param <T> 类型
     * @return ResultVO
     */
    public static <T> ResultVO getNoLogin() {
        return new ResultVO(2, "用户未登录，请重新登录");
    }


    /**
     * 用户没有操作权限
     *
     * @param <T> 类型
     * @return ResultVO
     */
    public static <T> ResultVO getNoAuthorization() {
        return new ResultVO(3, "用户没有操作权限，请重新登录");
    }
}
