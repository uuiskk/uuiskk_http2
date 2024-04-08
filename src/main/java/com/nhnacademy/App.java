package com.nhnacademy;

import com.nhnacademy.http.HttpRequestHandler;
import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class App 
{
    public static void main( String[] args ) throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(8080);){

            HttpRequestHandler httpRequestHandlerA = new HttpRequestHandler();
            HttpRequestHandler httpRequestHandlerB = new HttpRequestHandler();

            Thread threadA = new Thread(httpRequestHandlerA);
            threadA.start();

            Thread threadB = new Thread(httpRequestHandlerB);
            threadB.start();


            long count = 0;

            while(true){
                Socket client = serverSocket.accept();

                if(count%2==0){
                    httpRequestHandlerA.addRequest(client);
                }else{
                    httpRequestHandlerB.addRequest(client);
                }
                count++;
            }
        }
    }
}