package com.nhnacademy.http.channel;

import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class Channel {
    private final Queue<Socket> requestQueue;
    private long QUEUE_MAX_SIZE;

    public Channel(Queue<Socket> requestQueue) {
        this.requestQueue = new LinkedList<>();
    }

    public synchronized void addRequest(Socket client){
        while(requestQueue.size() >= QUEUE_MAX_SIZE){
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
