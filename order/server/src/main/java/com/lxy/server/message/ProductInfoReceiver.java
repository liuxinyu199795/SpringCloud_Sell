package com.lxy.server.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lxy.commom.ProductInfoOutput;
import com.lxy.server.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ProjectName: order
 * @Package: com.lxy.server.message
 * @ClassName: ProductInfoReceiver
 * @Author: XinyuLiu
 * @Date: 2019/5/16 19:30
 */
@Component
@Slf4j
public class ProductInfoReceiver {
    //定义常量
    private static final String PRODUCT_STOCK_TEMPLATE="product_stock_%s";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message){
        //从Message转换成ProductInfoOutPut
        List<ProductInfoOutput> productInfoOutputList = (List<ProductInfoOutput>) JsonUtil.fromJson(message,
                new TypeReference<List<ProductInfoOutput>>() {});
        log.info("从队列【{}】接收到消息:{}","productInfo",productInfoOutputList);


        //储存在redis中,对列表循环存储
        for (ProductInfoOutput productInfoOutput : productInfoOutputList) {
            stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE,productInfoOutput.getProductId()),
                    String.valueOf(productInfoOutput.getProductStock()));
        }

    }
}
