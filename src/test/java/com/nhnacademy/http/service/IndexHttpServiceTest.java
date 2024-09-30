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

package com.nhnacademy.http.service;

import com.nhnacademy.http.HttpRequestHandler;
import com.nhnacademy.http.request.HttpRequest;
import com.nhnacademy.http.request.HttpRequestImpl;
import com.nhnacademy.http.response.HttpResponse;
import com.nhnacademy.http.response.HttpResponseImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@Slf4j
class IndexHttpServiceTest {
    HttpService httpService;
    HttpRequest httpRequest;
    HttpResponse httpResponse;
    PrintWriter printWriter;
    StringWriter stringWriter;

    @BeforeEach
    void setUp() throws IOException {
        httpService = new IndexHttpService();

        httpRequest = Mockito.mock(HttpRequestImpl.class);
        Mockito.when(httpRequest.getRequestURI()).thenReturn("/index.html");

        httpResponse = Mockito.mock(HttpResponseImpl.class);

        // StringWriter를 사용하여 커스텀 버퍼 생성
        stringWriter = new StringWriter();
        //printWriter에 stringWriter 주입.
        printWriter = new PrintWriter(stringWriter);
        Mockito.when(httpResponse.getWriter()).thenReturn(printWriter);

    }

    @DisplayName("instance of HttpService")
    void constructor(){
        Assertions.assertInstanceOf(HttpService.class, new IndexHttpService());
    }

    @Test
    @DisplayName("doGet")
    void doGet() {
        Mockito.when(httpRequest.getMethod()).thenReturn("GET");

        httpService.service(httpRequest,httpResponse);
        String response = stringWriter.toString();

        log.debug("response:{}",response);
        Assertions.assertTrue(response.contains("200 OK"));
    }

    @Test
    @DisplayName("doPost : 405 method not allowed")
    void doPost(){
        Mockito.when(httpRequest.getMethod()).thenReturn("POST");
        Assertions.assertThrows(RuntimeException.class,()->{
            httpService.service(httpRequest,httpResponse);
        });
    }

}