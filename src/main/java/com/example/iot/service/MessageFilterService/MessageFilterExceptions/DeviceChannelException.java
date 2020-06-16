package com.example.iot.service.MessageFilterService.MessageFilterExceptions;

public class DeviceChannelException extends Exception {
    String msg;

    public DeviceChannelException(){
        msg = "设备数据通道不存在！";
    }

    public String getMsg(){
        return msg;
    }
}
