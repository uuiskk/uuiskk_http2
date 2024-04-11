package com.nhnacademy;

import com.nhnacademy.http.SimpleHttpServer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App 
{
    public static void main( String[] args ){
        SimpleHttpServer simpleHttpServer = new SimpleHttpServer();
        simpleHttpServer.start();
    }
}