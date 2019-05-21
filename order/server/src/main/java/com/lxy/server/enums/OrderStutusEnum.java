package com.lxy.server.enums;

import lombok.Getter;

@Getter
public enum OrderStutusEnum {
    NEW(0,"新订单"),
    FINSHED(1,"完结"),
    CANCEL(2,"取消"),
    ;
    private Integer code;
    private String message;

    OrderStutusEnum(Integer code, String message){
        this.code=code;
        this.message=message;
    }

}
