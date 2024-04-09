package com.nhnacademy.http;

import com.nhnacademy.http.channel.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpRequestHandler implements Runnable {

    private final RequestChannel requestChannel;

    public HttpRequestHandler(RequestChannel requestChannel) {
        this.requestChannel = requestChannel;
    }

    @Override
    public void run() {
        HttpJob httpJob = requestChannel.getHttpJob();
        httpJob.execute();
        run();
    }
}
