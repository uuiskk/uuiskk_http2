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

package com.nhnacademy.http.channel;

import com.nhnacademy.http.request.HttpRequest;
import com.nhnacademy.http.request.HttpRequestImpl;
import com.nhnacademy.http.response.HttpResponse;
import com.nhnacademy.http.response.HttpResponseImpl;
import com.nhnacademy.http.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

@Slf4j
public class HttpJob implements Executable {

    private final HttpRequest httpRequest;
    private final HttpResponse httpResponse;

    private final Socket client;

    public HttpJob(Socket client) {
        /*TODO#5 client null check, IllegalArgumentException 발생 합니다. 적절한 ErrorMessage를 작성하세요
            httpRequest, httpResponse, client 초기화 합니다.
         */

        this.httpRequest = null;
        this.httpResponse = null;
        this.client = null;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    @Override
    public void execute(){

        log.debug("method:{}", httpRequest.getMethod());
        log.debug("uri:{}", httpRequest.getRequestURI());
        log.debug("clinet-closed:{}",client.isClosed());

        String responseBody = null;
        String responseHeader = null;

        /*TODO#6 /index.html을 요청시  httpRequest.getRequestURI()에 해당되는 html 파일이 존재 하지 않는다면  Http Status Code : 404 Not Found 응답 합니다.
             - ex) /index.html 요청이 온다면 ->  /resources/index.html이 존재하지 않는다면 404 응답 합니다.
             - ResponseUtils.isExist(httpRequest.getRequestURI()) 이용하여 구현합니다.
             - ResponseUtils.tryGetBodyFromFile() - responseBody에 응답할 html 파일을 읽습니다
             - ResponseUtils.createResponseHeader() - responseHeader 를 생성 합니다.
        */
        if(!ResponseUtils.isExist(httpRequest.getRequestURI())){
            //404 - not -found
            responseBody = null;
            responseHeader = null;
        }else{
            //파일이 존재 한다면..
            /*TODO#8 responseBody에 응답할 html 파일을 읽습니다.
              - ResponseUtils.tryGetBodyFromFile(httpRequest.getRequestURI()) 이용하여 구현 합니다.
            */

            responseBody = null;
            responseHeader = null;
        }

        //TODO#12 BufferWriter를 사용 하여 responseHeader, responseBody를 client에게 응답 합니다.
        BufferedWriter bufferedWriter = null;

        //TODO#13 client에게 응답 후 cleint와 연결을 종료 합니다.

    }
}