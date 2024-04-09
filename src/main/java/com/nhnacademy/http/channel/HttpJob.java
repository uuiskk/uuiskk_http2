package com.nhnacademy.http.channel;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.Objects;

@Slf4j
public class HttpJob implements Executable {
    private final HttpRequest httpRequest;

    public HttpJob(Socket client) {
        this.httpRequest = new HttpRequestImpl(client);
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    @Override
    public void execute(){

        //TODO
        HttpRequestImpl httpRequestImpl = (HttpRequestImpl) this.httpRequest;
        Socket client = httpRequestImpl.getClient();

        log.debug("method:{}", httpRequestImpl.getMethod());
        log.debug("uri:{}", httpRequestImpl.getRequestURI());
        log.debug("clinet-closed:{}",httpRequestImpl.getClient().isClosed());

        if(httpRequestImpl.getRequestURI().equals("/favicon.ico")){
            return;
        }

        URL url = this.getClass().getResource(httpRequestImpl.getRequestURI());
        if(Objects.isNull(url)){
            log.debug("404-not found");
            try {
                client.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //404 처리
            return;
        }

        StringBuilder responseBody = new StringBuilder();

        try(InputStream inputStream = this.getClass().getResourceAsStream(httpRequestImpl.getRequestURI());
            BufferedReader reader =  new BufferedReader(new InputStreamReader(inputStream,"UTF-8"))){

            while(true) {
                String line = reader.readLine();
                if(Objects.isNull(line)){
                    break;
                }
                responseBody.append(line);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StringBuilder responseHeader = new StringBuilder();
        responseHeader.append(String.format("HTTP/1.0 200 OK%s",System.lineSeparator()));
        responseHeader.append(String.format("Server: HTTP server/0.1%s",System.lineSeparator()));
        responseHeader.append(String.format("Content-type: text/html; charset=%s%s","UTF-8",System.lineSeparator()));
        responseHeader.append(String.format("Connection: Closed%s",System.lineSeparator()));
        responseHeader.append(String.format("Content-Length:%d %s%s",responseBody.length(),System.lineSeparator(),System.lineSeparator()));

        try(BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))){
            bufferedWriter.write(responseHeader.toString());
            bufferedWriter.write(responseBody.toString());
            bufferedWriter.flush();
            log.debug("body:{}",responseBody.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
