package com.lxy.user.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @ProjectName: user
 * @Package: com.lxy.user.dataobject
 * @ClassName: UserInfo
 * @Author: XinyuLiu
 * @Date: 2019/5/18 0:04
 */
@Data
@Entity
public class UserInfo {
    @Id
    private String id;
    private String username;
    private String password;
    private String openid;
    private Integer role;
}
