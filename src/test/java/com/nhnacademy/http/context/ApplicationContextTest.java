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

package com.nhnacademy.http.context;

import com.nhnacademy.http.context.exception.ObjectNotFoundException;
import com.nhnacademy.http.service.IndexHttpService;
import com.nhnacademy.http.service.InfoHttpService;
import org.junit.jupiter.api.*;

import java.util.Objects;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationContextTest {
    @BeforeEach
    void setUp(){
        Context context = ContextHolder.getApplicationContext();
        context.setAttribute("indexHttpService", new IndexHttpService());
    }

    @Test
    @Order(1)
    void setAttribute() {
        Context context = ContextHolder.getApplicationContext();
        context.setAttribute("name",new Object());
        Assertions.assertTrue(Objects.nonNull(context.getAttribute("name")));
    }

    @Test
    @Order(2)
    void removeAttribute() {
        Context context = ContextHolder.getApplicationContext();
        String name = "indexHttpService";

        context.removeAttribute(name);
        Assertions.assertThrows(ObjectNotFoundException.class,()->{
            context.getAttribute(name);
        });
    }

    @Test
    @Order(3)
    void getAttribute() {
        Context context = ContextHolder.getApplicationContext();
        InfoHttpService infoHttpService = new InfoHttpService();
        context.setAttribute("infoHttpService", infoHttpService);
        Assertions.assertEquals(infoHttpService, context.getAttribute("infoHttpService") );
    }

    @Test
    @Order(4)
    @DisplayName("shared ContextHolder")
    void sharedContextHolder() throws InterruptedException {

        Thread thread1 = new Thread(()->{
            Context context = ContextHolder.getApplicationContext();
            context.setAttribute("counter", 10);
        });

        thread1.start();
        thread1.join();

        Thread thread2 = new Thread(()->{
            Context context = ContextHolder.getApplicationContext();
            int counter = (int) context.getAttribute("counter");
            counter = counter + 1;
            context.setAttribute("counter", counter);
        });

        thread2.start();
        thread2.join();

        Context context = ContextHolder.getApplicationContext();
        Assertions.assertEquals(11, context.getAttribute("counter"));
    }

}