package com.example.iot.service.MessageFilterService.MessageFilterExceptions;

public class DataFieldException extends Exception {
    String msg;

    public DataFieldException() {
        msg = "数据不符合数据通道规范！";
    }

    public String getMsg(){
        return msg;
    }
}
