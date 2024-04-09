package com.nhnacademy.http.service;

import com.nhnacademy.http.request.HttpRequest;
import com.nhnacademy.http.response.HttpResponse;
import com.nhnacademy.http.util.CounterUtils;
import com.nhnacademy.http.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class IndexHttpService implements HttpService{

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

        //TODO Body-설정
        String responseBody = null;

        try {
            responseBody = ResponseUtils.tryGetBodyFormFile(httpRequest.getRequestURI());
            responseBody = responseBody.replace("${count}", String.valueOf(CounterUtils.increaseAndGet()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //TODO Header-설정
        String responseHeader = ResponseUtils.createResponseHeader(200,"UTF-8",responseBody.length());

        //TODO PrintWriter 응답
        try(PrintWriter bufferedWriter = httpResponse.getWriter();){
            bufferedWriter.write(responseHeader);
            bufferedWriter.write(responseBody);
            bufferedWriter.flush();
            log.debug("body:{}",responseBody.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
