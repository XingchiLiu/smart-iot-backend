package com.example.iot.service.ConnectionService.connection.HTTP;

import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpClient {

    public static void main(String[] args) {
        // forsure http request uri
        String uri="index.htm";
        if (args.length!=0) {
            uri=args[0];
        }
        doGet("localhost",8080,uri);

    }

    public static void doGet(String host, int port, String uri) {
//        Socket socket=null;
//        try {
//            socket=new Socket(host,port);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            StringBuffer sb=new StringBuffer("GET "+uri+" HTTP/1.1\r\n");
//            sb.append("Accept: */*\r\n");
//            sb.append("Accept-Language: zh-cn\r\n");
//            sb.append("User-Agent: HTTPClient\r\n");
//            sb.append("Host: localhost:8080\r\n");
//            sb.append("Connection: Keep-Alive\r\n");
//
//            //send http request
//            OutputStream socketOut=socket.getOutputStream();
//            socketOut.write(sb.toString().getBytes());
//
//            Thread.sleep(2000);//sleep 2s ,wait response
//
//            //recieve
//            InputStream socketIn=socket.getInputStream();
//            int size=socketIn.available();
//            byte[] buffer=new byte[size];
//            socketIn.read(buffer);
//            System.out.println(new String(buffer));
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                socket.close();
//            } catch (Exception e2) {
//                e2.printStackTrace();
//            }
//        }

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("username","xiaoyi").build();
        Request request = new Request.Builder()
                .post(body)
                .url("http://locathost:8080")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){//回调的方法执行在子线程。
                }
            }
        });
    }

}
