/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2024. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package com.nhnacademy.http;

import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class SimpleHttpServer {

    private final int port;
    private static final int DEFAULT_PORT=8080;
    private final ServerSocket serverSocket;

    public SimpleHttpServer(){
        this(DEFAULT_PORT);
    }

    public SimpleHttpServer(int port) {
        if(port<=0){
            throw new IllegalArgumentException(String.format("invalid port :%d",port));
        }
        this.port = port;

        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void start() throws IOException {
        //TODO#1 - SocketServer를 생성 합니다. PORT = 8080
        try{
            while(!Thread.currentThread().isInterrupted()){
                Socket client = serverSocket.accept();
                //TODO#2 - Client와 서버가 연결 되면 HttpRequestHandler를 이용해서 Thread을 생성 합니다.
                Thread thread = new Thread(new HttpRequestHandler(client));
                thread.start();
            }
        }catch (Exception e){
            log.debug("{},",e.getMessage());
        }
    }
}
