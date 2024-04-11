package com.nhnacademy.http.context;

public interface Context {
    //Object를 등록합니다.
    void setAttribute(String name, Object object);
    //Object를 삭제합니다.
    void removeAttribute(String name);
    //Object를 얻습니다.
    Object getAttribute(String name);
}