package com.nhnacademy;

import com.nhnacademy.http.HttpRequestHandler;
import com.nhnacademy.http.SimpleHttpServer;
import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class App 
{
    public static void main( String[] args ) throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(8080);){
            while(true){
                Socket client = serverSocket.accept();
                Thread thread = new Thread(new HttpRequestHandler(client));
                thread.start();
            }
        }
    }
}