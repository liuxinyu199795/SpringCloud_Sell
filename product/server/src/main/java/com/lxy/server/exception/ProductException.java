package com.lxy.server.exception;

import com.lxy.server.enums.ResultEnum;

/**
 * @ProjectName: product
 * @Package: com.lxy.product.exception
 * @ClassName: ProductException
 * @Author: XinyuLiu
 * @Date: 2019/5/8 10:35
 */
public class ProductException extends RuntimeException{
    private Integer code;

    public ProductException(Integer code,String message){
        super(message);
        this.code=code;
    }
    public ProductException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code=resultEnum.getCode();
    }
}
