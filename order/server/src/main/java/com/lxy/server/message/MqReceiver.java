package com.lxy.server.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: order
 * @Package: com.lxy.server.message
 * @ClassName: MqReceiver 接收方
 * @Author: XinyuLiu
 * @Date: 2019/5/15 13:47
 */
@Slf4j
@Component
public class MqReceiver {
    //1.@RabbitListener(queues = "myQueue")，需要创建queue
    //2.自动创建队列，参数时queue[],@RabbitListener(queuesToDeclare = @Queue("myQueue"))
    //3.自动创建队列，queue和exchange绑定
    @RabbitListener(bindings = @QueueBinding(value = @Queue("myQueue"),exchange = @Exchange("myExchage")))
    public void process(String message){
        log.info("MqReceiver:{}",message);
    }
}
