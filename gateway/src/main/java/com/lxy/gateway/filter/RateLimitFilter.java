package com.lxy.gateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.lxy.gateway.exception.RateLimitException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

/**
 * @ProjectName: gateway
 * @Package: com.lxy.gateway.filter
 * @ClassName: RateFilter
 * @Author: XinyuLiu
 * @Date: 2019/5/17 17:16
 */
@Component
//采用令牌桶算法
public class RateLimitFilter extends ZuulFilter {

    //每秒放令牌的数量
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(100);
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return SERVLET_DETECTION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        if(!RATE_LIMITER.tryAcquire()){
            throw new RateLimitException();
        }
        return null;
    }
}
