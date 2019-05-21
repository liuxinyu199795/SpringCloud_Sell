package com.lxy.gateway.filter;

import com.lxy.gateway.constant.CookieConstant;
import com.lxy.gateway.constant.RedisConstant;
import com.lxy.gateway.util.CookieUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @ProjectName: gateway
 * @Package: com.lxy.gateway.filter
 * @ClassName: TokenFilter 权限拦截（区分买家和卖家）
 * @Author: XinyuLiu
 * @Date: 2019/5/17 16:35
 */
@Component
public class AuthFilter extends ZuulFilter {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();


        /**
         * /order/create 只能买家访问（cookie里有openid）
         * /order/finish 只能卖家访问（cookie里有token，并且对应的redis中有值）
         * /product/list 都可访问
         */

        if("/order/order/create".equals(request.getRequestURL())){
            Cookie cookie = CookieUtil.get(request,"openid");
            if(cookie==null||StringUtils.isEmpty(cookie.getValue())){
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }

        if("/order/order/finish".equals(request.getRequestURL())){
            Cookie cookie = CookieUtil.get(request,"token");
            if(cookie==null||StringUtils.isEmpty(cookie.getValue())
                    ||StringUtils.isEmpty(redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE,cookie.getValue())))){
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }
        return null;
    }
}
