package com.lxy.server.controller;

import com.lxy.server.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ProjectName: order
 * @Package: com.lxy.server.controller
 * @ClassName: SendMessageController
 * @Author: XinyuLiu
 * @Date: 2019/5/16 15:44
 */
@RestController
public class SendMessageController {
    @Autowired
    private StreamClient streamClient;

    @GetMapping("/sendMessage")
    public void process(){
        String message = "now:"+ new Date();
        streamClient.output().send(MessageBuilder.withPayload(message).build());

    }
}
