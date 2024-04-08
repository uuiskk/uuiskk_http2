package com.nhnacademy.http.channel;

import java.util.LinkedList;
import java.util.Queue;

public class RequestChannel {
    private final Queue<HttpRequest> requestQueue;
    private long QUEUE_MAX_SIZE = 10;

    public RequestChannel() {
        this.requestQueue = new LinkedList<>();
    }

    public synchronized void addRequest(HttpRequest httpRequest){
        while(requestQueue.size() >= QUEUE_MAX_SIZE){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        requestQueue.add(httpRequest);
        notifyAll();
    }

    public synchronized HttpRequest getRequest(){
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
}
