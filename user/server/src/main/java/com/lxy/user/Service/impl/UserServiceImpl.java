package com.lxy.user.Service.impl;

import com.lxy.user.Service.UserService;
import com.lxy.user.dataobject.UserInfo;
import com.lxy.user.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: user
 * @Package: com.lxy.user.Service.impl
 * @ClassName: UserServiceImpl
 * @Author: XinyuLiu
 * @Date: 2019/5/18 0:16
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo findByOpenid(String openid) {
        return userInfoRepository.findByOpenid(openid);
    }
}
