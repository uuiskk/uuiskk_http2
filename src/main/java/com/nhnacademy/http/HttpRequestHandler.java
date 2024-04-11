package com.nhnacademy.http;

import com.nhnacademy.http.channel.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class HttpRequestHandler implements Runnable {

    private final RequestChannel requestChannel;

    public HttpRequestHandler(RequestChannel requestChannel) {
        this.requestChannel = requestChannel;
    }

    @Override
    public void run() {
        Executable httpJob = requestChannel.getHttpJob();
        try {
            httpJob.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        run();
    }
}
