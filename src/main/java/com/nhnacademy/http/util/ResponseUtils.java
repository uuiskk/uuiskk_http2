package com.nhnacademy.http.util;

import java.io.*;
import java.net.URL;
import java.util.Objects;

public class ResponseUtils {
    private ResponseUtils(){}

    /*TODO#7 isExist를 구현합니다,
       ex) filePat=/index.html 이면 /resources/index.html이 존재하면 true, 존재하지 않다면 false를 반환 합니다.
   */
    public static boolean isExist(String filePath){
        URL url = ResponseUtils.class.getResource(filePath);
        return Objects.isNull(url) ? true : false;
    }

    /*TODO#9 tryGetBodyFromFile 구현 합니다.
    * ex) filePath = /index.html -> /resources/index.html 파일을 읽어서 반환 합니다.
    * */
    public static String tryGetBodyFromFile(String filePath) throws IOException {
        StringBuilder responseBody = new StringBuilder();
        try(InputStream inputStream = ResponseUtils.class.getResourceAsStream(filePath);
            BufferedReader reader =  new BufferedReader(new InputStreamReader(inputStream,"UTF-8"))){
            while(true) {
                String line = reader.readLine();
                if(Objects.isNull(line)){
                    break;
                }
                responseBody.append(line);
            }
        }
        return responseBody.toString();
    }

    /**
     *
     * @param httpStatusCode , 200 - OK
     * @param charset, default : UTF-8
     * @param contentLength, responseBody의 length
     * @return responseHeader를 String type으로 반환
     */
    public static String createResponseHeader(int httpStatusCode, String charset, int contentLength ){
        /* TODO#11 responseHeader를 생성 합니다.
        *
        * */
        StringBuilder responseHeader = new StringBuilder();
        responseHeader.append(String.format("HTTP/1.0 %d OK%s",httpStatusCode,System.lineSeparator()));
        responseHeader.append(String.format("Server: HTTP server/0.1%s",System.lineSeparator()));
        responseHeader.append(String.format("Content-type: text/html; charset=%s%s",charset,System.lineSeparator()));
        responseHeader.append(String.format("Connection: Closed%s",System.lineSeparator()));
        responseHeader.append(String.format("Content-Length:%d %s%s",contentLength,System.lineSeparator(),System.lineSeparator()));
        return responseHeader.toString();
    }

}
