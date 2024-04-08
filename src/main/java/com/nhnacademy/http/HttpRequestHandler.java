package com.nhnacademy.http;

import com.nhnacademy.http.channel.HttpRequest;
import com.nhnacademy.http.channel.RequestChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpRequestHandler implements Runnable {

    private final RequestChannel requestChannel;

    public HttpRequestHandler(RequestChannel requestChannel) {
        this.requestChannel = requestChannel;
    }

    @Override
    public void run() {
        HttpRequest httpRequest = requestChannel.getRequest();
        httpRequest.execute();
        run();
    }
}
