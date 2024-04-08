package com.nhnacademy.http;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

@Slf4j
public class HttpRequestHandler implements Runnable {

    private final Queue<Socket> requestQueue;
    private final int MAX_QUEUE_SIZE=10;

    public HttpRequestHandler() {
        requestQueue = new LinkedList<>();
    }

    public synchronized void addRequest(Socket client){
        //TODO queueSize >= MAX_QUEUE_SIZE 대기 합니다. 즉 queue에 데에티거 소비될 때 까지 client Socket을 Queue에 등록하는 작업을 대기 합니다.

        while(requestQueue.size()>=MAX_QUEUE_SIZE){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        requestQueue.add(client);
        notifyAll();
    }

    public synchronized Socket getRequest(){
        //Queue가 비어있다면 대기 합니다.
        while(requestQueue.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        notifyAll();
        return requestQueue.poll();
    }

    @Override
    public void run() {

        Socket client = getRequest();

        StringBuilder requestBuilder = new StringBuilder();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        ) {

            while (true) {
                String line = bufferedReader.readLine();
                //todo StringBuilder에 append
                requestBuilder.append(line);
                log.debug("line:{}", line);
                if (Objects.isNull(line) || line.length() == 0) {
                    //todo 종료 조건 null or size==0
                    break;
                }
            }
            //todo RequestBuilder에 append된 데이터를 parcing 하여 HttpRequest가 동작할 수 있도록 구현합니다.

            StringBuilder responseBody = new StringBuilder();
            responseBody.append("<html>");
            responseBody.append("<body>");
            responseBody.append("<h1>hello java</h1>");
            responseBody.append("</body>");
            responseBody.append("</html>");

            StringBuilder responseHeader = new StringBuilder();

            responseHeader.append(String.format("HTTP/1.0 200 OK%s",System.lineSeparator()));
            responseHeader.append(String.format("Server: HTTP server/0.1%s",System.lineSeparator()));
            responseHeader.append(String.format("Content-type: text/html; charset=%s%s","UTF-8",System.lineSeparator()));
            responseHeader.append(String.format("Connection: Closed%s",System.lineSeparator()));
            responseHeader.append(String.format("Content-Length:%d %s%s",responseBody.length(),System.lineSeparator(),System.lineSeparator()));

            bufferedWriter.write(responseHeader.toString());
            bufferedWriter.write(responseBody.toString());
            bufferedWriter.flush();
            client.close();
        }catch (IOException e){
            if (Objects.nonNull(client)){
                try {
                    client.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        run();
    }
}
