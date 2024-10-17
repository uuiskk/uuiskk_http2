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

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

//TODO#2 - Context를 구현합니다.
//Context에는 객체를 생성 후 등록 / 삭제 할 수 있습니다. 즉 공유할 수 있는 환경 입니다.
public class ApplicationContext  implements Context {
    ConcurrentMap<String, Object> objectMap;

    public ApplicationContext() {
        this.objectMap = new ConcurrentHashMap<>();
    }

    @Override
    public void setAttribute(String name, Object object) {
        if(Objects.isNull(object)){
            throw new IllegalArgumentException();
        }
        objectMap.put(name, object);
    }

    @Override
    public void removeAttribute(String name) {
        if(Objects.isNull(name) || name.isBlank()){
            throw new IllegalArgumentException();
        }
        objectMap.remove(name);
    }

    @Override
    public Object getAttribute(String name) {
        if(Objects.isNull(name) || name.isBlank()){
            throw new IllegalArgumentException();
        }
        if(!objectMap.containsKey(name)){
            throw new ObjectNotFoundException("존재하지 않는 컨텍스트");
        }
        return objectMap.get(name);
    }
}
