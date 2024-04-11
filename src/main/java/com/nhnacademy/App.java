package com.nhnacademy;

import com.nhnacademy.http.HttpRequestHandler;
import com.nhnacademy.http.SimpleHttpServer;
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
    public static void main( String[] args ){
        SimpleHttpServer simpleHttpServer = new SimpleHttpServer();
    }
}