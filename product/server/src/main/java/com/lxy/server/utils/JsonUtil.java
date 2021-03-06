package com.lxy.server.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @ProjectName: product
 * @Package: com.lxy.server.utils
 * @ClassName: JsonUtil
 * @Author: XinyuLiu
 * @Date: 2019/5/13 19:02
 */
public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 转换为json字符串
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Object fromJson(String string,Class classType){
        try {
            return objectMapper.readValue(string,classType);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
