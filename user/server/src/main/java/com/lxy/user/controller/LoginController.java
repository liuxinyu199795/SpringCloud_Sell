package com.lxy.user.controller;

import VO.ResultVO;
import com.lxy.user.Service.UserService;
import com.lxy.user.constant.CookieConstant;
import com.lxy.user.constant.RedisConstant;
import com.lxy.user.dataobject.UserInfo;
import com.lxy.user.enums.ResultEnum;
import com.lxy.user.enums.RoleEnum;
import com.lxy.user.utils.CookieUtil;
import com.lxy.user.utils.ResultVOUtil;
import com.sun.corba.se.impl.oa.toa.TOA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: user
 * @Package: com.lxy.user.controller
 * @ClassName: LoginController
 * @Author: XinyuLiu
 * @Date: 2019/5/20 10:22
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/buyer")
    public ResultVO buyer(@RequestParam("openid")String openid,
                          HttpServletResponse response){
        //1.openid和数据库里的数据是否匹配
        UserInfo userInfo=userService.findByOpenid(openid);
        if(userInfo==null){
            return ResultVOUtil.error(ResultEnum.LOGIN_ERROR);
        }
        //2.判断角色
        if(RoleEnum.BUYER.getCode()!=userInfo.getRole()){
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }
        //3.设置cookie
        CookieUtil.set(response,CookieConstant.OPENID,openid,CookieConstant.expire);
        return ResultVOUtil.success();
    }

    @GetMapping("/seller")
    public ResultVO seller(@RequestParam("openid")String openid,
                          HttpServletRequest request,
                          HttpServletResponse response){
        //判断是否已登录
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if(cookie!=null&&
                !StringUtils.isEmpty(redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE,cookie.getValue())))){
            return ResultVOUtil.success();
        }
        //1.openid和数据库里的数据是否匹配
        UserInfo userInfo=userService.findByOpenid(openid);
        if(userInfo==null){
            return ResultVOUtil.error(ResultEnum.LOGIN_ERROR);
        }
        //2.判断角色
        if(RoleEnum.SELLER.getCode()!=userInfo.getRole()){
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }
        //3.redis设置key=UUID,value=xyz
        String token = UUID.randomUUID().toString();
        Integer expire = CookieConstant.expire;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_TEMPLATE,token),
                openid,
                expire,
                TimeUnit.SECONDS);

        //4.设置cookie设置token=UUID
        CookieUtil.set(response,CookieConstant.TOKEN,token,CookieConstant.expire);
        return ResultVOUtil.success();

    }
}
