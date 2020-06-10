package com.example.iot.service.ConnectionService.connectionCore;

import com.example.iot.service.ConnectionService.connection.ConnectionManagement;
import com.example.iot.service.ConnectionService.connection.connectionImpl.HTTPConnectionImpl;
import com.example.iot.service.ConnectionService.connection.connectionImpl.MQTTConnectionImpl;


public class ConnectionCore {

    public ConnectionManagement connectionManagement = null;

    public void getConnection(int type){
        switch (type){
            case 0:
                connectionManagement = new MQTTConnectionImpl();
                break;
            case 1:
                connectionManagement = new HTTPConnectionImpl();
                break;
        }

        if(connectionManagement != null){
            connectionManagement.getConnection();
        }
    }

    public static void main(String[] args){
        ConnectionCore core = new ConnectionCore();
        core.getConnection(1);
//        ((MQTTConnectionImpl)core.connectionManagement)
//                .addSub(new String[] {"test1","test2"},0);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间
//        Date date = new Date();// 获取当前时间
//        ((MQTTConnectionImpl)core.connectionManagement)
//                .addPub("test1",0,"{\"deviceName\":\"test\",\"message\":\"成功！\",\"data\":[{\"humidity\":10,\"temperature\":20}]}");
//
//
//        ((MQTTConnectionImpl)core.connectionManagement)
//                .addPub("test1",0,"{\"deviceName\":\"test\",\"message\":\"成功！\",\"data\":[{\"humidity\":10,\"temperature\":20}]}");

    }
}
