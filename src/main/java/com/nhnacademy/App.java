package com.nhnacademy;

import com.nhnacademy.http.HttpRequestHandler;
import com.nhnacademy.http.channel.HttpJob;
import com.nhnacademy.http.channel.RequestChannel;
import com.nhnacademy.http.context.Context;
import com.nhnacademy.http.context.ContextHolder;
import com.nhnacademy.http.service.IndexHttpService;
import com.nhnacademy.http.service.InfoHttpService;
import com.nhnacademy.http.util.CounterUtils;
import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class App 
{
    public static void main( String[] args ) throws IOException {
        //TODO Context에 HttpService Object 등록
        Context context = ContextHolder.getApplicationContext();
        context.setAttribute("/index.html",new IndexHttpService());
        context.setAttribute("/info.html", new InfoHttpService());
        context.setAttribute(CounterUtils.CONTEXT_COUNTER_NAME,0l);

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

        }
    }
}