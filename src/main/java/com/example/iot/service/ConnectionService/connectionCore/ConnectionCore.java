package com.example.iot.service.ConnectionService.connectionCore;

import com.alibaba.fastjson.JSON;
import com.example.iot.controller.VO.MessageForm;
import com.example.iot.service.ConnectionService.connection.ConnectionManagement;
import com.example.iot.service.ConnectionService.connection.connectionImpl.HTTPConnectionService;
import com.example.iot.service.ConnectionService.connection.connectionImpl.MQTTConnectionService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class ConnectionCore {

    public ConnectionManagement connectionManagement = null;

    public void getConnection(int type){
        switch (type){
            case 0:
                connectionManagement = new MQTTConnectionService();
                break;
            case 1:
                connectionManagement = new HTTPConnectionService();
                break;
        }

        if(connectionManagement != null){
            connectionManagement.getConnection();
        }
    }

    public static void main(String[] args){
        ConnectionCore core = new ConnectionCore();
        core.getConnection(0);
        ((MQTTConnectionService)core.connectionManagement)
                .addSub("/device/channel",0);

        ((MQTTConnectionService)core.connectionManagement)
                .addPub("/device/channel",0,"{\"deviceName\":\"test\",\"message\":\"成功！\",\"data\":[{\"humidity\":10,\"temperature\":20}]}");


        ((MQTTConnectionService)core.connectionManagement)
                .addPub("/device/channel",0,"{\"deviceName\":\"test\",\"message\":\"成功！\",\"data\":[{\"humidity\":10,\"temperature\":20}]}");

    }
}
