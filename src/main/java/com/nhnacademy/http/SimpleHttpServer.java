package com.nhnacademy.http;

import com.nhnacademy.http.channel.HttpJob;
import com.nhnacademy.http.channel.RequestChannel;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


@Slf4j
public class SimpleHttpServer {

    private final int port;
    private static final int DEFAULT_PORT=8080;

    public SimpleHttpServer(){
        this(DEFAULT_PORT);
    }
    public SimpleHttpServer(int port) {
        if(port<=0){
            throw new IllegalArgumentException(String.format("Invalid Port:%d",port));
        }
        this.port = port;
    }

    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(8080);){
            RequestChannel requestChannel = new RequestChannel();

            HttpRequestHandler httpRequestHandlerA = new HttpRequestHandler(requestChannel);
            HttpRequestHandler httpRequestHandlerB = new HttpRequestHandler(requestChannel);

            Thread threadA = new Thread(httpRequestHandlerA);
            threadA.start();

            Thread threadB = new Thread(httpRequestHandlerB);
            threadB.start();

            while(true){
                Socket client = serverSocket.accept();
                requestChannel.addHttpJob(new HttpJob(client));
            }
        }catch (IOException e){
            log.error("server error:{}",e);
        }
    }
}