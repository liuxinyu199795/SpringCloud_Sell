package com.lxy.user.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @ProjectName: user
 * @Package: com.lxy.user.enums
 * @ClassName: RoleEnum
 * @Author: XinyuLiu
 * @Date: 2019/5/20 10:58
 */
@Getter
public enum RoleEnum {

    BUYER(1,"买家"),
    SELLER(2,"卖家"),
    ;
    private Integer code;
    private String message;

    RoleEnum(Integer code,String message){
        this.code=code;
        this.message=message;
    }
}
