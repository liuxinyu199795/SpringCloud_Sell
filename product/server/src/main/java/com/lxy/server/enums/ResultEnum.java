package com.lxy.server.enums;

import lombok.Getter;

/**
 * @ProjectName: product
 * @Package: com.lxy.product.enums
 * @ClassName: ResultEnum
 * @Author: XinyuLiu
 * @Date: 2019/5/8 10:41
 */
@Getter
public enum  ResultEnum {
    PRODUCT_NO_EXIST(1,"商品不存在"),
    PRODUCT_STOCK_ERROR(2,"库存有误"),
    ;
    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
