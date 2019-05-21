package com.lxy.server;

import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ProjectName: order
 * @Package: com.lxy.server
 * @ClassName: MqSenderTest 测试MQ方法 发送方
 * @Author: XinyuLiu
 * @Date: 2019/5/16 14:57
 */
@Component
public class MqSenderTest extends ServerApplicationTests {
    @Autowired
    public AmqpTemplate amqpTemplate;

    @Test
    public void send(){
        amqpTemplate.convertAndSend("myQueue","now"+new Date());
    }
}
