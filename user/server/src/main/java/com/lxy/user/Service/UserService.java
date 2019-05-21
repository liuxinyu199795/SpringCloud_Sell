package com.lxy.user.Service;

import com.lxy.user.dataobject.UserInfo;

/**
 * @ProjectName: user
 * @Package: com.lxy.user.Service
 * @ClassName: UserService
 * @Author: XinyuLiu
 * @Date: 2019/5/18 0:14
 */
public interface UserService {
    /**
     * 通过openid来查询用户信息
     * @param openid
     * @return
     */
    UserInfo findByOpenid(String openid);
}
