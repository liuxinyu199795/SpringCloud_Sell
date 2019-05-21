package com.lxy.server.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @ProjectName: order
 * @Package: com.lxy.server.message
 * @ClassName: StreamClient
 * @Author: XinyuLiu
 * @Date: 2019/5/16 15:31
 */
public interface StreamClient {
    String INPUT="input";
    String OUTPUT="output";
    @Input(StreamClient.INPUT)
    SubscribableChannel input();

    @Output(StreamClient.OUTPUT)
    MessageChannel output();

}
