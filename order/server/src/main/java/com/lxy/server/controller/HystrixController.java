package com.lxy.server.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * @ProjectName: order
 * @Package: com.lxy.server.controller
 * @ClassName: HystrixController
 * @Author: XinyuLiu
 * @Date: 2019/5/20 18:19
 */
@RestController
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {

    @GetMapping("/getProductInfoList")
    public String getProductInfoList(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://127.0.0.1/product/listForOrder",
                Arrays.asList("157875196366160022"),String.class);
    }
    private String defaultFallback(){
        return "默认提示:太拥挤了，请稍后再试";
    }
}
