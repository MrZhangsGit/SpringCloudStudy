package com.my.filter.util;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/1/3
 */
public class HttpHelper {
    public static String getRequestBody(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = null;
        try {
            if (inputStream != null) {
                reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = reader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("---getRequestBody  Body:" + JSON.toJSONString(stringBuilder));
        return stringBuilder.toString();
    }
}
