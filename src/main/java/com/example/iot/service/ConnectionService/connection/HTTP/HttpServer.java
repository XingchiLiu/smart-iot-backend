package com.example.iot.service.ConnectionService.connection.HTTP;

import com.example.iot.service.ConnectionService.connectionCore.MessageReceiver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    int port;
    ServerSocket serverSocket;
//    MessageReceiver messageReceiver = new MessageReceiverImpl();

    public void connect(){
        port=8080;
        String request = null;
        try {
            serverSocket=new ServerSocket(port);
            System.out.println("server is running on port:"+serverSocket.getLocalPort());
            while (true) {
                try {
                    final Socket socket=serverSocket.accept();
                    System.out.println("build a new tcp link with client,the client address:"+
                            socket.getInetAddress()+":"+socket.getPort());
                    request = service(socket);
                } catch (Exception e) {
                    e.printStackTrace();
//                    MessageReceiver.returnResponse("Error", "Error!");
                }
//                MessageReceiver.returnResponse("OK", request);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

//    public static void main(String[] args) {
//        int port;
//        ServerSocket serverSocket;
//
//        try {
//            port=Integer.parseInt(args[0]);
//        } catch (Exception e) {
//            System.out.println("port=8080 (default)");
//            port=8080;
//        }
//
//        try {
//            serverSocket=new ServerSocket(port);
//            System.out.println("server is running on port:"+serverSocket.getLocalPort());
//            while (true) {
//                try {
//                    final Socket socket=serverSocket.accept();
//                    System.out.println("biuld a new tcp link with client,the cient address:"+
//                            socket.getInetAddress()+":"+socket.getPort());
//                    service(socket);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//    }
    public static String service(Socket socket)throws Exception{
        //read http information
        InputStream socketIn=socket.getInputStream();
        Thread.sleep(500);
        int size=socketIn.available();
        byte[] buffer=new byte[size];
        socketIn.read(buffer);
        String request=new String(buffer);
        System.out.println(request);

        //
        //get http request first line
        String firstLineOfRequest=request.substring(0, request.indexOf("\r\n"));
        String[] parts=firstLineOfRequest.split(" ");
        String uri=parts[1];

        //mime
        String contentType;
        if(uri.indexOf("html")!=-1||uri.indexOf("htm")!=-1)
            contentType="text/html";
        else {
            contentType="application/octet-stream";
        }

        //create http response
        //the first line
        String responseFirstLine="HTTP/1.1 200 OK\r\n";
        //http response head
        String responseHeade="Content-Type:"+contentType+"\r\n";

        //send http response result
        OutputStream socketOut=socket.getOutputStream();
        //send http response first line
        socketOut.write(responseFirstLine.getBytes());
        //send http response heade
        socketOut.write(responseHeade.getBytes());

        InputStream in=HttpServer.class.getResourceAsStream("root/"+uri);
        //send content
        socketOut.write("\r\n".getBytes());
        int len=0;
        buffer=new byte[128];
        if (in!=null) {
            while((len=in.read(buffer))!=-1){
                System.out.println(new String(buffer));
                socketOut.write(buffer,0,len);
            }
        }

        Thread.sleep(1000);
        //close tcp link
        socket.close();
        return request;
    }
}
