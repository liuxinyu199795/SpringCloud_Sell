package com.lxy.server.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: order
 * @Package: com.lxy.server.message
 * @ClassName: StreamReceiver
 * @Author: XinyuLiu
 * @Date: 2019/5/16 15:40
 */
@Component
@Slf4j
@EnableBinding(StreamClient.class)
public class StreamReceiver {
    //监听器
    @StreamListener(StreamClient.INPUT)
    public void process(Object message){
        log.info("StreamReceiver:{}",message);
    }
}
