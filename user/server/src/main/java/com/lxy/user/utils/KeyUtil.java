package com.lxy.user.utils;

import java.util.Random;

public class KeyUtil {
    /*
    *   生成唯一的组件
    *   格式：时间戳+随机数
    */
    public static synchronized String genUniqueKey(){
        Random random = new Random();
        Integer number = random.nextInt(90000)+10000;
        return System.currentTimeMillis()+String.valueOf(number);
    }
}
