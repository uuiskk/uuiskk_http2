package com.nhnacademy.http.channel;

import com.nhnacademy.http.request.HttpRequest;
import com.nhnacademy.http.request.HttpRequestImpl;
import com.nhnacademy.http.response.HttpResponse;
import com.nhnacademy.http.response.HttpResponseImpl;
import com.nhnacademy.http.service.HttpService;
import com.nhnacademy.http.service.IndexHttpService;
import com.nhnacademy.http.service.InfoHttpService;
import com.nhnacademy.http.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.Objects;

@Slf4j
public class HttpJob implements Executable {

    private final HttpRequest httpRequest;
    private final HttpResponse httpResponse;

    private final Socket client;

    public HttpJob(Socket client) {
        this.httpRequest = new HttpRequestImpl(client);
        this.httpResponse = new HttpResponseImpl(client);
        this.client = client;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    @Override
    public void execute(){

        log.debug("method:{}", httpRequest.getMethod());
        log.debug("uri:{}", httpRequest.getRequestURI());
        log.debug("clinet-closed:{}",client.isClosed());

        if(httpRequest.getRequestURI().equals("/favicon.ico")){
            return;
        }

        if(!ResponseUtils.isExist(httpRequest.getRequestURI())){
            try {
                //404 - not -found
                client.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        //TODO RequestURI에 따른 URL을 호출 합니다.
        if(httpRequest.getRequestURI().equals("/index.html")) {
            HttpService indexHttpService = new IndexHttpService();
            indexHttpService.service(httpRequest, httpResponse);
        }else if(httpRequest.getRequestURI().equals("/info.html")) {
            HttpService infoHttpService = new InfoHttpService();
            infoHttpService.service(httpRequest, httpResponse);
        }

        try {
            if(Objects.nonNull(client) && client.isConnected()) {
                client.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}