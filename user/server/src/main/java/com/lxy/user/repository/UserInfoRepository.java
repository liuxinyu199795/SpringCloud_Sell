package com.lxy.user.repository;

import com.lxy.user.dataobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ProjectName: user
 * @Package: com.lxy.user.repository
 * @ClassName: UserInfoRepository
 * @Author: XinyuLiu
 * @Date: 2019/5/18 0:09
 */
public interface UserInfoRepository extends JpaRepository<UserInfo,String> {
    UserInfo findByOpenid(String openid);
}
