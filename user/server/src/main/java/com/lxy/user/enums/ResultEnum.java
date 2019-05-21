package com.lxy.user.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    LOGIN_ERROR(1,"登陆失败"),
    ROLE_ERROR(2,"角色权限有误"),
    ;
    private Integer code;
    private String message;
    ResultEnum(Integer code,String message){
        this.code=code;
        this.message=message;
    }
}
